package org.c41.expression4j;

import jdk.internal.org.objectweb.asm.Type;

import java.lang.reflect.Method;

public abstract class CallExpression extends Expression{

    final Method method;
    final Expression[] parameters;

    CallExpression(Method method, Expression[] parameterExpressions){
        this.method = method;
        this.parameters = parameterExpressions.clone();

        Class<?>[] parameterTypes = method.getParameterTypes();
        if(method.isVarArgs()){
            if(parameterTypes.length > parameterExpressions.length){
                throw CompileExpression.parametersNotMatch(parameterTypes, parameterExpressions);
            }
        }
        else{
            if(parameterTypes.length != parameterExpressions.length){
                throw CompileExpression.parametersNotMatch(parameterTypes, parameterExpressions);
            }
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return method.getReturnType();
    }

    void pushParameters(BodyEmit ctx){
        Class<?>[] parameterTypes = method.getParameterTypes();

        int i = 0;
        while(i < parameterTypes.length - 1){
            parameters[i].emit(ctx);
            i++;
        }
        if(method.isVarArgs()){
            int varArgStart = i;

            Class<?> arrayElementType = parameterTypes[varArgStart].getComponentType();

            Expressions.newArray(
                    arrayElementType,
                    new IntConstantExpression(parameters.length - varArgStart)
            ).emit(ctx);
            for(int index = 0; i < parameters.length; index++, i++){
                ctx.dup();
                Expressions.constant(index).emit(ctx);
                Expressions.cast(parameters[i], arrayElementType).emit(ctx);
                ctx.tastore(arrayElementType);
            }
        }
        else{
            this.parameters[i].emit(ctx);
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