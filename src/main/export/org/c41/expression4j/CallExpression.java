package org.c41.expression4j;

import java.lang.reflect.Executable;

public abstract class CallExpression extends Expression{

    final Executable method;
    final Class<?>[] parameterTypes;
    final Expression[] parameterExpressions;

    CallExpression(Executable method, Expression[] parameterExpressions){
        this.method = method;
        this.parameterExpressions = parameterExpressions.clone();
        this.parameterTypes = method.getParameterTypes();

        if(parameterTypes.length > parameterExpressions.length){
            throw CompileException.parametersNotMatch(parameterTypes, parameterExpressions);
        }
        if (!isVarArgs() && parameterTypes.length != parameterExpressions.length){
            throw CompileException.parametersNotMatch(parameterTypes, parameterExpressions);
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
    abstract void emitRead(BodyEmit bodyEmit);

    @Override
    public final Class<?> getExpressionType(){
        Class<?> returnType = getReturnType();
        if(returnType == void.class){
            return null;
        }
        return returnType;
    }

    public abstract Class<?> getReturnType();

    void pushParameters(BodyEmit ctx){
        Class<?>[] parameterTypes = method.getParameterTypes();

        int i = 0;
        while(i < parameterTypes.length - 1){
            parameterExpressions[i].emitRead(ctx);
            i++;
        }
        if(isVarArgs()){
            int varArgStart = i;

            Class<?> arrayElementType = parameterTypes[varArgStart].getComponentType();

            Expressions.NewArray(
                arrayElementType,
                new IntConstantExpression(parameterExpressions.length - varArgStart)
            ).emitRead(ctx);
            for(int index = 0; i < parameterExpressions.length; index++, i++){
                ctx.dup();
                Expressions.Constant(index).emitRead(ctx);
                Expressions.Cast(parameterExpressions[i], arrayElementType).emitRead(ctx);
                ctx.astore(arrayElementType);
            }
        }
        else{
            if(i < parameterTypes.length){
                this.parameterExpressions[i].emitRead(ctx);
            }
        }
    }

}

