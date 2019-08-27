package org.c41.expression4j;

import java.util.Arrays;
import java.util.List;

public class CatchBlock {

    private final List<Expression> expressions;
    private final ParameterExpression parameterExpression;

    CatchBlock(ParameterExpression parameterExpression, Expression[] expressions){
        this.expressions = Arrays.asList(expressions);
        this.parameterExpression = parameterExpression;
    }

    public Class<?> getTargetType(){
        return parameterExpression.getExpressionType();
    }

    public ParameterExpression getExceptionParameter(){
        return parameterExpression;
    }

    public Iterable<Expression> getBodyExpressions(){
        return expressions;
    }

}
