package org.c41.expression4j;

public class ParameterExpression extends Expression{

    private final Class<?> type;

    ParameterExpression(Class<?> type){
        this.type = type;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        ctx.load(this);
    }

    @Override
    void emitWrite(BodyEmit ctx, Expression expression, boolean isBalance) {
        ctx.ParameterStack.declareParameter(this);
        expression.emitRead(ctx);
        if(!isBalance){
            ctx.dup(expression.getExpressionType());
        }
        ctx.store(this);
    }

    @Override
    public Class<?> getExpressionType() {
        return type;
    }

    @Override
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
