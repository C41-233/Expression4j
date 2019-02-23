package org.c41.expression4j;

public abstract class Expression {

    void emitRead(BodyEmit ctx){
        throw CompileException.badOperator();
    }

    void emitBalance(BodyEmit ctx) { throw CompileException.badOperator(); }

    public abstract Class<?> getExpressionType();

}
