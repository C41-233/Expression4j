package org.c41.expression4j;

public class CatchExpression {

    private Class<?> targetType;
    private Expression expression;

    CatchExpression(Class<?> targetType, Expression expression){
        this.targetType = targetType;
        this.expression = expression;
    }

    public Class<?> getTargetType(){
        return targetType;
    }

    public Expression getBodyExpression(){
        return expression;
    }

}
