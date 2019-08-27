package org.c41.expression4j;

import java.lang.reflect.Constructor;

public class NewExpression extends CallExpression{

    NewExpression(Constructor<?> method, Expression[] parameterExpressions) {
        super(method, parameterExpressions);
    }

    @Override
    public Class<?> getReturnType() {
        return ((Constructor)method).getDeclaringClass();
    }

    @Override
    void emitRead(BodyEmit ctx) {
        Constructor<?> constructor = (Constructor) method;
        ctx.newobject(constructor.getDeclaringClass());
        ctx.dup();
        pushParameters(ctx);
        ctx.invokespecial(constructor);
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        sb.append("new ");
        sb.append(method.getDeclaringClass().getName());
        sb.append("(");
        for(int i=0; i<parameterExpressions.length; i++){
            parameterExpressions[i].toString(sb, CodeStyle.None);
            if(i < parameterExpressions.length - 1){
                sb.append(", ");
            }
        }
        sb.append(")");
    }
}
