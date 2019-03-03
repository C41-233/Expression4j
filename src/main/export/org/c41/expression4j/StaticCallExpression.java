package org.c41.expression4j;

import java.lang.reflect.Method;

public class StaticCallExpression extends CallExpression{

    StaticCallExpression(Method method, Expression[] parameters) {
        super(method, parameters);
    }

    @Override
    void emitRead(BodyEmit ctx) {
        pushParameters(ctx);
        ctx.invokestatic((Method) method);
    }

    @Override
    public Class<?> getReturnType() {
        return ((Method)method).getReturnType();
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        Class<?> type = method.getDeclaringClass();
        sb.append(type.getSimpleName()).append('.').append(method.getName());
        sb.append('(');
        for (int i = 0; i < parameterExpressions.length; i++) {
            Expression expression = parameterExpressions[i];
            expression.toString(sb, CodeStyle.Advance);
            if(i != parameterExpressions.length - 1){
                sb.append(", ");
            }
        }
        sb.append(')');
        if(CodeStyle.is(mask, CodeStyle.Statement)){
            sb.append(';');
        }
    }
}
