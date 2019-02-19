package org.c41.expression4j;

import org.c41.expression4j.annotations.ValueExpression;

import java.lang.reflect.Field;

public class Expressions {

    public static <TLambda> TLambda complie(Class<TLambda> lambdaClass, BlockExpression body, Parameter... parameters){
        ClassEmit<TLambda> cl = new ClassEmit<>(lambdaClass);
        return cl.emit(body, parameters);
    }

    public static <TLambda, TExpression extends Expression & ValueExpression> TLambda complie(Class<TLambda> lambdaClass, TExpression body, Parameter... parameters){
        ClassEmit<TLambda> cl = new ClassEmit<>(lambdaClass);
        return cl.emit(body, parameters);
    }

    public static <T1 extends Expression & ValueExpression, T2 extends Expression & ValueExpression> AddExpression add(T1 left, T2 right){
        return new AddExpression(left, right);
    }

    public static <T1 extends Expression & ValueExpression, T2 extends Expression & ValueExpression> SubtractExpression subtract(T1 left, T2 right){
        return new SubtractExpression(left, right);
    }

    public static Parameter parameter(Class<?> type){
        return new Parameter(type);
    }

    public static <T extends Expression & ValueExpression> FieldExpression field(T self, Field field){
        return new FieldExpression(self, field);
    }
}
