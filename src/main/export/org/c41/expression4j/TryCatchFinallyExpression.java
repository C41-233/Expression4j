package org.c41.expression4j;

import org.objectweb.asm.Label;

public class TryCatchFinallyExpression extends Expression {

    private final Expression tryExpression;
    private final Expression finallyExpression;
    private final CatchExpression[] catchExpressions;

    TryCatchFinallyExpression(Expression tryExpression, CatchExpression[] catchExpressions, Expression finallyExpression){
        this.tryExpression = tryExpression;
        this.catchExpressions = catchExpressions;
        this.finallyExpression = finallyExpression;
    }

    @Override
    void emit(BodyEmit ctx) {
        Label tryStart = new Label();
        Label tryEnd = new Label();

        int catchCount = catchExpressions.length;
        Label[] catchStarts = new Label[catchCount];
        for(int i = 0; i < catchCount; i++){
            catchStarts[i] = new Label();
        }
        Label finallyStart = new Label();
        Label finallyRethrowStart = new Label();

        ctx.label(tryStart);
        tryExpression.emit(ctx);
        ctx.label(tryEnd);
        ctx.jmp(finallyStart);

        for(int i = 0; i < catchCount; i++){
            ctx.label(catchStarts[i]);

            ParameterExpression e = new ParameterExpression(catchExpressions[i].getTargetType());
            ctx.declareParameter(e);
            ctx.tstore(e);
            catchExpressions[i].getBodyExpression().emit(ctx);

            ctx.jmp(finallyStart);
        }

        ctx.label(finallyRethrowStart);
        finallyExpression.emit(ctx);
        ctx.athrow();

        ctx.label(finallyStart);
        finallyExpression.emit(ctx);

        for(int i=0; i < catchCount; i++){
            ctx.exception(
                catchExpressions[i].getTargetType(),
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
