package org.c41.expression4j;

public class BlockExpression extends Expression{

    private Expression[] expressions;

    public BlockExpression(Expression[] expressions) {
        this.expressions = expressions.clone();
    }

    @Override
    void emit(BodyEmit ctx) {
        for(Expression expression : expressions){
            expression.emit(ctx);
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
