package org.c41.expression4j;

public class EmptyExpression extends Expression{

    EmptyExpression(){
    }

    @Override
    void emitRead(BodyEmit bodyEmit) {
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
