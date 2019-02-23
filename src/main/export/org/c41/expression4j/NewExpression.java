package org.c41.expression4j;

import java.lang.reflect.Constructor;

public class NewExpression extends CallExpression{

    NewExpression(Constructor<?> method, Expression[] parameterExpressions) {
        super(method, parameterExpressions);
    }

    @Override
    public Class<?> getExpressionType() {
        return ((Constructor)method).getDeclaringClass();
    }

    @Override
    void emit(BodyEmit ctx) {
        Constructor<?> constructor = (Constructor) method;
        ctx.newobject(constructor.getDeclaringClass());
        ctx.dup();
        pushParameters(ctx);
        ctx.invokespecial(constructor);
    }

}
