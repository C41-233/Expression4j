package org.c41.expression4j;

import java.util.StringJoiner;

public class CompileExpression extends RuntimeException{


    private CompileExpression(String msg) {
        super(msg);
    }

    private CompileExpression(String msg, Exception e) {
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

    public static CompileExpression emitFail(Exception e) {
        return new CompileExpression("compile fail", e);
    }

    public static CompileExpression parametersNotMatch(Class<?>[] parameterTypes, ParameterExpression[] parameters) {
        StringJoiner joiner1 = new StringJoiner(",", "[", "]");
        for(Class<?> cl : parameterTypes){
            joiner1.add(cl.toString());
        }StringJoiner joiner2 = new StringJoiner(",", "[", "]");
        for(ParameterExpression parameter : parameters){
            joiner2.add(parameter.getExpressionType().toString());
        }
        return new CompileExpression("parameter types not match expected " + joiner1 + " actual " + joiner2);
    }
}
