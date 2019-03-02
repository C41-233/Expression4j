package org.c41.expression4j;

public class BlockExpression extends Expression{

    private Expression[] expressions;

    BlockExpression(Expression[] expressions) {
        this.expressions = expressions;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        ctx.ParameterStack.pushScope();
        for (int i = 0; i < expressions.length; i++) {
            Expression expression = expressions[i];
            expression.emitBalance(ctx);
        }
        ctx.ParameterStack.popScope();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

}
