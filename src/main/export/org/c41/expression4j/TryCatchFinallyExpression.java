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
        Label tryStart = new Label();
        Label tryEnd = new Label();

        int catchCount = catchBlocks.length;
        Label[] catchStarts = new Label[catchCount];
        for(int i = 0; i < catchCount; i++){
            catchStarts[i] = new Label();
        }
        Label finallyStart = new Label();
        Label finallyRethrowStart = new Label();

        ctx.pushScope();
        {
            ctx.label(tryStart);
            tryExpression.emit(ctx);
            ctx.label(tryEnd);
            ctx.jmp(finallyStart);
        }
        ctx.popScope();

        for(int i = 0; i < catchCount; i++){
            ctx.pushScope();
            ctx.label(catchStarts[i]);

            ParameterExpression e = new ParameterExpression(catchBlocks[i].getTargetType());
            ctx.declareParameter(e);
            ctx.tstore(e);
            catchBlocks[i].getBodyExpression().emit(ctx);

            ctx.jmp(finallyStart);
            ctx.popScope();
        }

        ctx.pushScope();
            ctx.label(finallyRethrowStart);
            finallyExpression.emit(ctx);
            ctx.athrow();
        ctx.popScope();

        ctx.pushScope();
            ctx.label(finallyStart);
            finallyExpression.emit(ctx);
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
