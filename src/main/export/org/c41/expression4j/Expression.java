package org.c41.expression4j;

public abstract class Expression {

    void emit(BodyEmit ctx){
        throw CompileException.badOperator();
    }

    public abstract Class<?> getExpressionType();

    Class<?> getStackType(){
        return getExpressionType();
    }
}
