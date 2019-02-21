package org.c41.expression4j;

public class ParameterExpression extends Expression{

    private final Class<?> type;

    ParameterExpression(Class<?> type){
        this.type = type;
    }

    @Override
    void emit(BodyEmit emit) {
        emit.tload(this);
    }

    @Override
    public Class<?> getExpressionType() {
        return type;
    }
}
