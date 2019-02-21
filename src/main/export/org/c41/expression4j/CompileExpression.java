package org.c41.expression4j;

import java.util.StringJoiner;

public class CompileExpression extends RuntimeException{


    private CompileExpression(String msg) {
        super(msg);
    }

    private CompileExpression(String msg, Throwable e) {
        super(msg, e);
    }

    public static CompileExpression parameterNotDeclare(ParameterExpression parameter) {
        return new CompileExpression("parameter not declare");
    }

    public static CompileExpression writeValueExpression() {
        return new CompileExpression("value expression cannot assign");
    }

    public static CompileExpression badOperator() {
        return new CompileExpression("bad operator");
    }

    public static CompileExpression emitFail(String msg, Throwable e) {
        return new CompileExpression("compile fail\n" + msg, e);
    }

    public static CompileExpression parametersNotMatch(Class<?>[] parameterTypes, Expression[] parameters) {
        StringJoiner joiner1 = new StringJoiner(",", "[", "]");
        for(Class<?> cl : parameterTypes){
            joiner1.add(cl.getTypeName());
        }StringJoiner joiner2 = new StringJoiner(",", "[", "]");
        for(Expression parameter : parameters){
            joiner2.add(parameter.getExpressionType().getTypeName());
        }
        return new CompileExpression("parameter types not match expected " + joiner1 + " actual " + joiner2);
    }

    public static CompileExpression fieldNotFoundException(Class<System> type, String name) {
        return new CompileExpression("field " + name + " not found in class " + type);
    }

    public static CompileExpression badCast(Class<?> from, Class<?> type) {
        return new CompileExpression(from + " cannot cast to " + type);
    }
}
