package org.c41.expression4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Expressions {

    public static <TLambda> TLambda compile(Class<TLambda> lambdaClass, Expression body, ParameterExpression... parameters){
        return compile(lambdaClass, lambdaClass.getClassLoader(), body, parameters);
    }

    public static <TLambda> TLambda compile(Class<TLambda> lambdaClass, ClassLoader cl, Expression body, ParameterExpression... parameters){
        ClassEmit<TLambda> ce = new ClassEmit<>(lambdaClass);
        return ce.emit(cl, body, parameters.clone());
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
        return new MemberFieldExpression(self, field);
    }

    public static StaticFieldExpression field(Class<System> type, String name) {
        try {
            Field field = type.getField(name);
            return new StaticFieldExpression(field);
        } catch (NoSuchFieldException e) {
            throw CompileException.fieldNotFoundException(type, name);
        }
    }

    public static StringConstantExpression constant(String value){
        return new StringConstantExpression(value);
    }

    public static IntConstantExpression constant(int value){
        return new IntConstantExpression(value);
    }

    public static AssignExpression assign(Expression self, Expression value){
        return new AssignExpression(self, value);
    }

    public static CastExpression cast(Expression expression, Class<?> type){
        return new CastExpression(expression, type);
    }

    public static BlockExpression block(Expression... expressions){
        return new BlockExpression(expressions.clone());
    }

    public static Expression call(Expression self, Method method, Expression... parameters) {
        return new MethodCallExpression(self, method, parameters);
    }

    public static Expression call(Method method, Expression... parameters) {
        return new StaticCallExpression(method, parameters);
    }

    public static Expression newArray(Class<?> elementType, Expression length) {
        return new NewArrayExpression(elementType, length);
    }

    public static CatchExpression catchBlock(Class<?> targetType, Expression bodyExpression){
        return new CatchExpression(targetType, bodyExpression);
    }

    public static TryCatchFinallyExpression tryCatchFinally(Expression tryExpression, Expression finallyExpression, CatchExpression... catchExpressions){
        return new TryCatchFinallyExpression(tryExpression, catchExpressions.clone(), finallyExpression);
    }

}
