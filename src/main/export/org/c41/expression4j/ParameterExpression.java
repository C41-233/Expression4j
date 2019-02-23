package org.c41.expression4j;

public class ParameterExpression extends Expression{

    private final Class<?> type;

    ParameterExpression(Class<?> type){
        this.type = type;
    }

    @Override
    void emit(BodyEmit ctx) {
        ctx.load(this);
    }

    void emitAssign(BodyEmit ctx, Expression expression) {
        expression.emit(ctx);
        ctx.store(this);
    }

    @Override
    public Class<?> getExpressionType() {
        return type;
    }

}
