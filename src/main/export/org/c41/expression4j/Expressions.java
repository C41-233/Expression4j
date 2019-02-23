package org.c41.expression4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Expressions {

    public static <TLambda> TLambda compile(Class<TLambda> lambdaClass, Expression body, ParameterExpression... parameters){
        return compile(lambdaClass, lambdaClass.getClassLoader(), body, parameters);
    }

    public static <TLambda> TLambda compile(Class<TLambda> lambdaClass, ClassLoader cl, Expression body, ParameterExpression... parameters){
        return ClassEmit.emit(lambdaClass, cl, body, parameters.clone());
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

    public static EmptyExpression empty(){
        return new EmptyExpression();
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

    public static CatchBlock catchBlock(Class<?> targetType, Expression bodyExpression){
        return new CatchBlock(targetType, bodyExpression);
    }

    public static TryCatchFinallyExpression tryCatchFinally(Expression tryExpression, Expression finallyExpression, CatchBlock... catchBlocks){
        return tryCatchFinally(tryExpression, catchBlocks, finallyExpression);
    }

    public static TryCatchFinallyExpression tryCatchFinally(Expression tryExpression, CatchBlock[] catchBlocks, Expression finallyExpression){
        return new TryCatchFinallyExpression(tryExpression, catchBlocks.clone(), finallyExpression);
    }

    public static TryCatchFinallyExpression tryCatch(Expression tryExpression, CatchBlock... catchBlocks){
        return new TryCatchFinallyExpression(tryExpression, catchBlocks.clone(), null);
    }

    public static ReturnExpression ret(Expression expression){
        return new ReturnExpression(expression);
    }

    public static ReturnExpression ret(){
        return new ReturnExpression();
    }

}
