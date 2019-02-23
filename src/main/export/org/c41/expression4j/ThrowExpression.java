package org.c41.expression4j;

public class ThrowExpression extends Expression{

    private final Expression expression;

    ThrowExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    void emit(BodyEmit ctx) {
        expression.emit(ctx);
        ctx.athrow();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
