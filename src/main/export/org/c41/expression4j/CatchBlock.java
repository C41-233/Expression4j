package org.c41.expression4j;

import java.util.Arrays;
import java.util.List;

public class CatchBlock {

    private final Class<?> targetType;
    private final List<Expression> expressions;

    CatchBlock(Class<?> targetType, Expression[] expressions){
        this.targetType = targetType;
        this.expressions = Arrays.asList(expressions);
    }

    public Class<?> getTargetType(){
        return targetType;
    }

    public Iterable<Expression> getBodyExpressions(){
        return expressions;
    }

}
