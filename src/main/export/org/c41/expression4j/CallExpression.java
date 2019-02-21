package org.c41.expression4j;

import jdk.internal.org.objectweb.asm.Type;

import java.lang.reflect.Method;

public abstract class CallExpression extends Expression{

    final Method method;
    final Class<?>[] parameterTypes;
    final Expression[] parameterExpressions;

    CallExpression(Method method, Expression[] parameterExpressions){
        this.method = method;
        this.parameterExpressions = parameterExpressions.clone();
        this.parameterTypes = method.getParameterTypes();

        if(parameterTypes.length > parameterExpressions.length){
            throw CompileExpression.parametersNotMatch(parameterTypes, parameterExpressions);
        }
        if (!isVarArgs() && parameterTypes.length != parameterExpressions.length){
            throw CompileExpression.parametersNotMatch(parameterTypes, parameterExpressions);
        }
    }

    private boolean isVarArgs(){
        if(method.isVarArgs()){
            if(parameterExpressions.length > parameterTypes.length){
                return true;
            }
            Class<?> targetType = parameterTypes[parameterTypes.length - 1];
            Class<?> inType = parameterExpressions[parameterTypes.length - 1].getExpressionType();
            return targetType != inType;
        }
        return false;
    }

    @Override
    public Class<?> getExpressionType() {
        return method.getReturnType();
    }

    void pushParameters(BodyEmit ctx){
        Class<?>[] parameterTypes = method.getParameterTypes();

        int i = 0;
        while(i < parameterTypes.length - 1){
            parameterExpressions[i].emit(ctx);
            i++;
        }
        if(isVarArgs()){
            int varArgStart = i;

            Class<?> arrayElementType = parameterTypes[varArgStart].getComponentType();

            Expressions.newArray(
                arrayElementType,
                new IntConstantExpression(parameterExpressions.length - varArgStart)
            ).emit(ctx);
            for(int index = 0; i < parameterExpressions.length; index++, i++){
                ctx.dup();
                Expressions.constant(index).emit(ctx);
                Expressions.cast(parameterExpressions[i], arrayElementType).emit(ctx);
                ctx.tastore(arrayElementType);
            }
        }
        else{
            this.parameterExpressions[i].emit(ctx);
        }
    }

}

final class MethodCallExpression extends CallExpression{

    private final Expression self;

    MethodCallExpression(Expression self, Method method, Expression[] parameters) {
        super(method, parameters);
        this.self = self;
    }

    @Override
    void emit(BodyEmit ctx) {
        self.emit(ctx);
        pushParameters(ctx);
        ctx.invokevirtual(
            Type.getInternalName(method.getDeclaringClass()),
            method.getName(),
            Type.getMethodDescriptor(method)
        );
    }
}

final class StaticCallExpression extends CallExpression{

    StaticCallExpression(Method method, Expression[] parameters) {
        super(method, parameters);
    }

    @Override
    void emit(BodyEmit ctx) {
        pushParameters(ctx);
        ctx.invokestatic(method);
    }
}