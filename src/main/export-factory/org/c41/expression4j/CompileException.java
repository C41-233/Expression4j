package org.c41.expression4j;

import java.util.StringJoiner;

public class CompileException extends RuntimeException{

    private CompileException(String msg) {
        super(msg);
    }

    private CompileException(String msg, Throwable e) {
        super(msg, e);
    }

    public static CompileException parameterNotDeclare(ParameterExpression parameter) {
        return new CompileException("Parameter not declare");
    }

    public static CompileException writeValueExpression() {
        return new CompileException("value expression cannot Assign");
    }

    public static CompileException badOperator() {
        return new CompileException("bad operator");
    }

    public static CompileException emitFail(String msg, Throwable e) {
        return new CompileException("Compile fail\n" + msg, e);
    }

    public static CompileException parametersNotMatch(Class<?>[] parameterTypes, Expression[] parameters) {
        StringJoiner joiner1 = new StringJoiner(",", "[", "]");
        for(Class<?> cl : parameterTypes){
            joiner1.add(cl.getTypeName());
        }StringJoiner joiner2 = new StringJoiner(",", "[", "]");
        for(Expression parameter : parameters){
            joiner2.add(parameter.getExpressionType().getTypeName());
        }
        return new CompileException("Parameter types not match expected " + joiner1 + " actual " + joiner2);
    }

    public static CompileException fieldNotFoundException(Class<System> type, String name) {
        return new CompileException("Field " + name + " not found in class " + type);
    }

    public static CompileException badCast(Class<?> from, Class<?> type) {
        return new CompileException(from + " cannot cast to " + type);
    }

    public static CompileException notSupport() {
        return new CompileException("not support");
    }
}
