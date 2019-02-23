package org.c41.expression4j;

public class ReturnExpression extends Expression{

    private final Expression expression;

    ReturnExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    void emit(BodyEmit ctx) {
        expression.emit(ctx);
        ctx.ret(expression.getExpressionType());
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
