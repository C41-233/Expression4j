package org.c41.expression4j;

import java.lang.reflect.Method;

public class StaticCallExpression extends CallExpression{

    StaticCallExpression(Method method, Expression[] parameters) {
        super(method, parameters);
    }

    @Override
    void emit(BodyEmit ctx) {
        pushParameters(ctx);
        ctx.invokestatic((Method) method);
    }

    @Override
    public Class<?> getExpressionType() {
        return ((Method)method).getReturnType();
    }

}
