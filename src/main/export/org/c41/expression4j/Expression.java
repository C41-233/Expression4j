package org.c41.expression4j;

public abstract class Expression {

    abstract void emit(BodyEmit bodyEmit);

    public abstract Class<?> getExpressionType();

}