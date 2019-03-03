package org.c41.expression4j;

import org.objectweb.asm.Label;

import java.util.List;

public class TryCatchFinallyExpression extends Expression {

    TryCatchFinallyExpression(){
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    @Override
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}

final class RuntimeTryCatchExpression extends TryCatchFinallyExpression{

    private final Expression tryExpression;
    private final CatchBlock[] catchBlocks;

    RuntimeTryCatchExpression(Expression tryExpression, CatchBlock[] catchBlocks) {
        this.tryExpression = tryExpression;
        this.catchBlocks = catchBlocks;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        Label tryStart = ctx.declareLabel();
        Label tryEnd = ctx.declareLabel();
        Label exit = ctx.declareLabel();

        int catchCount = catchBlocks.length;
        Label[] catchStarts = new Label[catchCount];
        for(int i = 0; i < catchCount; i++){
            catchStarts[i] = ctx.declareLabel();
        }
        ctx.ParameterStack.pushScope();
        {
            ctx.label(tryStart);
            tryExpression.emitBalance(ctx);
            ctx.label(tryEnd);
            ctx.jmp(exit);
        }
        ctx.ParameterStack.popScope();

        for(int i = 0; i < catchCount; i++){
            ctx.ParameterStack.pushScope();
            {
                ctx.label(catchStarts[i]);

                ParameterExpression e = Expressions.Parameter(catchBlocks[i].getTargetType());
                ctx.ParameterStack.declareParameter(e);
                ctx.store(e);
                for(Expression expression : catchBlocks[i].getBodyExpressions()){
                    expression.emitBalance(ctx);
                }

                if(i != catchCount - 1){
                    ctx.jmp(exit);
                }
            }
            ctx.ParameterStack.popScope();
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
}

final class RuntimeTryFinallyExpression extends TryCatchFinallyExpression{

    private final Expression tryExpression;
    private final Expression finallyExpression;

    RuntimeTryFinallyExpression(Expression tryExpression, Expression finallyExpression) {
        this.tryExpression = tryExpression;
        this.finallyExpression = finallyExpression;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        //label
        Label tryStart = ctx.declareLabel();
        Label tryEnd = ctx.declareLabel();

        ctx.RedirectReturnControlFlow.pushRedirect();

        //try block
        ctx.ParameterStack.pushScope();
        {
            ctx.label(tryStart);
            tryExpression.emitBalance(ctx);
            ctx.label(tryEnd);
        }
        ctx.ParameterStack.popScope();

        List<RedirectFrame> frames = ctx.RedirectReturnControlFlow.popRedirect();

        Label finallyContinue = null;
        if(frames.size() == 0){
            finallyContinue = ctx.declareLabel();
            ctx.jmp(finallyContinue);
        }

        //finally rethrow
        Label finallyRethrow = ctx.declareLabel();
        ctx.ParameterStack.pushScope();
        {
            ctx.label(finallyRethrow);
            finallyExpression.emitBalance(ctx);
            ctx.athrow();
        }
        ctx.ParameterStack.popScope();

        //finally return/break/continue
        for(RedirectFrame frame : frames){
            ctx.RedirectReturnControlFlow.declareLabel(frame.JmpLabel);
            ctx.ParameterStack.pushScope();
            {
                ctx.label(frame.JmpLabel);
                finallyExpression.emitBalance(ctx);
                if(frame.RedirectLabel == null){
                    ctx.ret(ctx.getReturnType());
                }
                else{
                    ctx.jmp(frame.RedirectLabel);
                }
            }
            ctx.ParameterStack.popScope();
        }

        //finally with no redirect
        if(finallyContinue != null){
            ctx.ParameterStack.pushScope();
            {
                ctx.label(finallyContinue);
                finallyExpression.emitBalance(ctx);
            }
            ctx.ParameterStack.popScope();
        }

        //exception table
        ctx.exception(
            null,
            tryStart,
            tryEnd,
            finallyRethrow
        );
    }
}

final class RuntimeTryCatchFinallyExpression extends TryCatchFinallyExpression{

    private final RuntimeTryFinallyExpression delegate;

    RuntimeTryCatchFinallyExpression(Expression tryExpression, CatchBlock[] catchBlocks, Expression finallyExpression){
        this.delegate = new RuntimeTryFinallyExpression(
            new RuntimeTryCatchExpression(tryExpression, catchBlocks),
            finallyExpression
        );
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        delegate.emitBalance(ctx);
    }

}