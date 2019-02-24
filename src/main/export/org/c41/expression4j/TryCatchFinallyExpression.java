package org.c41.expression4j;

import org.objectweb.asm.Label;

public class TryCatchFinallyExpression extends Expression {

    private final Expression tryExpression;
    private final Expression finallyExpression;
    private final CatchBlock[] catchBlocks;

    TryCatchFinallyExpression(Expression tryExpression, CatchBlock[] catchBlocks, Expression finallyExpression){
        this.tryExpression = tryExpression;
        this.catchBlocks = catchBlocks;
        this.finallyExpression = finallyExpression;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        if(finallyExpression != null){
            emitTryCatchFinally(ctx);
        }
        else{
            emitTryCatch(ctx);
        }
    }

    private void emitTryCatch(BodyEmit ctx){
        Label tryStart = new Label();
        Label tryEnd = new Label();
        Label exit = new Label();

        int catchCount = catchBlocks.length;
        Label[] catchStarts = new Label[catchCount];
        for(int i = 0; i < catchCount; i++){
            catchStarts[i] = new Label();
        }
        ctx.pushScope();
        {
            ctx.label(tryStart);
            tryExpression.emitBalance(ctx);
            ctx.label(tryEnd);
            ctx.jmp(exit);
        }
        ctx.popScope();

        for(int i = 0; i < catchCount; i++){
            ctx.pushScope();
            {
                ctx.label(catchStarts[i]);

                ParameterExpression e = Expressions.Parameter(catchBlocks[i].getTargetType());
                ctx.declareParameter(e);
                ctx.store(e);
                for(Expression expression : catchBlocks[i].getBodyExpressions()){
                    expression.emitBalance(ctx);
                }

                if(i != catchCount - 1){
                    ctx.jmp(exit);
                }
            }
            ctx.popScope();
        }

        ctx.label(exit);

        for(int i=0; i < catchCount; i++){
            ctx.exception(
                catchBlocks[i].getTargetType(),
                tryStart,
                tryEnd,
                catchStarts[i]
            );
        }
    }

    /**
     * try-catch-finally block
     * need handle finally
     */
    private void emitTryCatchFinally(BodyEmit ctx){
        //label
        Label tryStart = new Label();
        Label tryEnd = new Label();

        int catchCount = catchBlocks.length;
        Label[] catchStarts = new Label[catchCount];
        for(int i = 0; i < catchCount; i++){
            catchStarts[i] = new Label();
        }
        Label finallyStart = new Label();
        Label finallyRethrowStart = new Label();

        //try
        ctx.RedirectReturnControlFlow.pushRedirect(finallyStart);
        ctx.pushScope();
        {
            ctx.label(tryStart);
            tryExpression.emitBalance(ctx);
            ctx.label(tryEnd);
            ctx.jmp(finallyStart);
        }
        ctx.popScope();

        //catch
        for(int i = 0; i < catchCount; i++){
            ctx.pushScope();
            {
                ctx.label(catchStarts[i]);

                ParameterExpression e = Expressions.Parameter(catchBlocks[i].getTargetType());
                ctx.declareParameter(e);
                ctx.store(e);
                for(Expression expression : catchBlocks[i].getBodyExpressions()){
                    expression.emitBalance(ctx);
                }

                ctx.jmp(finallyStart);
            }
            ctx.popScope();
        }
        boolean alreadyReturn = ctx.RedirectReturnControlFlow.isTriggered();
        ctx.RedirectReturnControlFlow.popRedirect();

        //finally rethrow
        ctx.pushScope();
        {
            ctx.label(finallyRethrowStart);
            finallyExpression.emitBalance(ctx);
            ctx.athrow();
        }
        ctx.popScope();

        //finally
        ctx.pushScope();
        {
            ctx.label(finallyStart);
            finallyExpression.emitBalance(ctx);
            if(alreadyReturn){
                ctx.ret(ctx.getReturnType());
            }
        }
        ctx.popScope();

        for(int i=0; i < catchCount; i++){
            ctx.exception(
                catchBlocks[i].getTargetType(),
                tryStart,
                tryEnd,
                catchStarts[i]
            );
        }
        ctx.exception(
            null,
            tryStart,
            tryEnd,
            finallyRethrowStart
        );
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
