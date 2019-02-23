package org.c41.expression4j;

public class BlockExpression extends Expression{

    private Expression[] expressions;

    BlockExpression(Expression[] expressions) {
        this.expressions = expressions;
    }

    @Override
    void emit(BodyEmit ctx) {
        ctx.pushScope();
        for (int i = 0; i < expressions.length; i++) {
            Expression expression = expressions[i];
            expression.emit(ctx);
        }
        ctx.popScope();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    @Override
    Class<?> getStackType() {
        if(expressions.length == 0){
            return null;
        }
        return expressions[expressions.length - 1].getStackType();
    }
}
