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
    void emit(BodyEmit ctx) {
        Expression finallyExpression = this.finallyExpression == null ? Expressions.empty() : this.finallyExpression;

        Label tryStart = new Label();
        Label tryEnd = new Label();

        int catchCount = catchBlocks.length;
        Label[] catchStarts = new Label[catchCount];
        for(int i = 0; i < catchCount; i++){
            catchStarts[i] = new Label();
        }
        Label finallyStart = new Label();
        Label finallyRethrowStart = new Label();

        boolean alreadyReturn = false;

        ctx.pushRedirectControlFlow(finallyStart);
        ctx.pushScope();
        {
            ctx.label(tryStart);
            tryExpression.emit(ctx);
            ctx.label(tryEnd);
            ctx.jmp(finallyStart);
        }
        ctx.popScope();
        alreadyReturn = alreadyReturn || ctx.isRedirectTrigger();
        ctx.popRedirectControlFlow();

        for(int i = 0; i < catchCount; i++){
            ctx.pushScope();
            ctx.pushRedirectControlFlow(finallyStart);
            {
                ctx.label(catchStarts[i]);

                ParameterExpression e = Expressions.parameter(catchBlocks[i].getTargetType());
                ctx.declareParameter(e);
                ctx.store(e);
                catchBlocks[i].getBodyExpression().emit(ctx);

                ctx.jmp(finallyStart);
            }
            ctx.popScope();
            alreadyReturn = alreadyReturn || ctx.isRedirectTrigger();
            ctx.popRedirectControlFlow();
        }

        ctx.pushScope();
        {
            ctx.label(finallyRethrowStart);
            finallyExpression.emit(ctx);
            ctx.athrow();
        }
        ctx.popScope();

        ctx.pushScope();
        {
            ctx.label(finallyStart);
            finallyExpression.emit(ctx);
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
