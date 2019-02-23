package org.c41.expression4j;

public class BlockExpression extends Expression{

    private Expression[] expressions;

    BlockExpression(Expression[] expressions) {
        this.expressions = expressions;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        ctx.pushScope();
        for (int i = 0; i < expressions.length; i++) {
            Expression expression = expressions[i];
            expression.emitRead(ctx);
        }
        ctx.popScope();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

}
