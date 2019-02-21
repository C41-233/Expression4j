package org.c41.expression4j;

import java.lang.reflect.Field;

public class Expressions {

    public static <TLambda> TLambda complie(Class<TLambda> lambdaClass, BlockExpression body, ParameterExpression... parameters){
        ClassEmit<TLambda> cl = new ClassEmit<>(lambdaClass);
        return cl.emit(body, parameters);
    }

    public static <TLambda> TLambda complie(Class<TLambda> lambdaClass, Expression body, ParameterExpression... parameters){
        ClassEmit<TLambda> cl = new ClassEmit<>(lambdaClass);
        return cl.emit(body, parameters);
    }

    public static AddExpression add(Expression left, Expression right){
        return new AddExpression(left, right);
    }

    public static SubtractExpression subtract(Expression left, Expression right){
        return new SubtractExpression(left, right);
    }

    public static ParameterExpression parameter(Class<?> type){
        return new ParameterExpression(type);
    }

    public static FieldExpression field(Expression self, Field field){
        return new FieldExpression(self, field);
    }

    public static AssignExpression assign(Expression self, Expression value){
        return new AssignExpression(self, value);
    }

    public static CastExpression cast(Expression expression, Class<?> type){
        return new CastExpression(expression, type);
    }

    public static BlockExpression block(Expression... expressions){
        return new BlockExpression(expressions);
    }
}
