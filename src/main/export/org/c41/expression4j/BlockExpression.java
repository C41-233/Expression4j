package org.c41.expression4j;

public class BlockExpression extends Expression{

    private Expression[] expressions;

    BlockExpression(Expression[] expressions) {
        this.expressions = expressions;
    }

    @Override
    void emit(BodyEmit ctx) {
        ctx.pushScope();
        for(Expression expression : expressions){
            expression.emit(ctx);
        }
        ctx.popScope();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
