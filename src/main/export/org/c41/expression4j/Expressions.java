package org.c41.expression4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Expressions {

    public static <TLambda> TLambda Compile(Class<TLambda> lambdaClass, Expression body, ParameterExpression... parameters){
        return Compile(lambdaClass, lambdaClass.getClassLoader(), body, parameters);
    }

    public static <TLambda> TLambda Compile(Class<TLambda> lambdaClass, ClassLoader cl, Expression body, ParameterExpression... parameters){
        return ClassEmit.emit(lambdaClass, cl, body, parameters.clone());
    }

    public static AddExpression Add(Expression left, Expression right){
        return new AddExpression(left, right);
    }

    public static SubtractExpression Subtract(Expression left, Expression right){
        return new SubtractExpression(left, right);
    }

    public static ParameterExpression Parameter(Class<?> type){
        return new ParameterExpression(type);
    }

    public static FieldExpression Field(Expression self, Field field){
        return new MemberFieldExpression(self, field);
    }

    public static FieldExpression Field(Expression self, String fieldName){
        try {
            Field field = self.getExpressionType().getField(fieldName);
            return Field(self, field);
        } catch (NoSuchFieldException e) {
            throw CompileException.emitFail("field not found", e);
        }

    }

    public static StaticFieldExpression Field(Class<System> type, String name) {
        try {
            Field field = type.getField(name);
            return new StaticFieldExpression(field);
        } catch (NoSuchFieldException e) {
            throw CompileException.fieldNotFoundException(type, name);
        }
    }

    public static StringConstantExpression Constant(String value){
        return new StringConstantExpression(value);
    }

    public static IntConstantExpression Constant(int value){
        return new IntConstantExpression(value);
    }

    public static AssignExpression Assign(Expression self, Expression value){
        return new AssignExpression(self, value);
    }

    public static CastExpression Cast(Expression expression, Class<?> type){
        return new CastExpression(expression, type);
    }

    public static BlockExpression Block(Expression... expressions){
        return new BlockExpression(expressions.clone());
    }

    public static EmptyExpression Empty(){
        return new EmptyExpression();
    }

    public static MethodCallExpression Call(Expression self, Method method, Expression... parameters) {
        return new MethodCallExpression(self, method, parameters);
    }

    public static StaticCallExpression Call(Method method, Expression... parameters) {
        return new StaticCallExpression(method, parameters);
    }

    public static StaticCallExpression Call(Class<?> owner, String name, Class<?>[] parameterTypes, Expression... parameters){
        try {
            Method method = owner.getMethod(name, parameterTypes);
            return Call(method, parameters);
        } catch (NoSuchMethodException e) {
            throw CompileException.emitFail("method not found", e);
        }
    }

    public static MethodCallExpression Call(Expression self, String name, Class<?>[] parameterTypes, Expression... parameters){
        try {
            Method method = self.getExpressionType().getMethod(name, parameterTypes);
            return Call(self, method, parameters);
        } catch (NoSuchMethodException e) {
            throw CompileException.emitFail("method not found", e);
        }
    }

    public static NewExpression New(Constructor<?> constructor, Expression... parameters){
        return new NewExpression(constructor, parameters.clone());
    }

    public static NewArrayExpression NewArray(Class<?> elementType, Expression length) {
        return new NewArrayExpression(elementType, length);
    }

    public static CatchBlock CatchBlock(Class<?> targetType, Expression... expressions){
        return new CatchBlock(targetType, expressions);
    }

    public static TryCatchFinallyExpression TryCatchFinally(Expression tryExpression, Expression finallyExpression, CatchBlock... catchBlocks){
        return TryCatchFinally(tryExpression, catchBlocks, finallyExpression);
    }

    public static TryCatchFinallyExpression TryCatchFinally(Expression tryExpression, CatchBlock[] catchBlocks, Expression finallyExpression){
        return new TryCatchFinallyExpression(tryExpression, catchBlocks.clone(), finallyExpression);
    }

    public static TryCatchFinallyExpression TryCatch(Expression tryExpression, CatchBlock... catchBlocks){
        return new TryCatchFinallyExpression(tryExpression, catchBlocks.clone(), null);
    }

    public static ReturnExpression Return(Expression expression){
        return new ReturnExpression(expression);
    }

    public static ReturnExpression Return(){
        return new ReturnExpression();
    }

    public static ThrowExpression Throw(Expression expression){
        return new ThrowExpression(expression);
    }

}
