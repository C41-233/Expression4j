package org.c41.expression4j;

public class CompileExpression extends RuntimeException{


    private CompileExpression(String msg) {
        super(msg);
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
}
