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

    void emitWrite(BodyEmit ctx, Expression expression) {
        expression.emitRead(ctx);
        ctx.store(this);
    }

    @Override
    public Class<?> getExpressionType() {
        return type;
    }

}
