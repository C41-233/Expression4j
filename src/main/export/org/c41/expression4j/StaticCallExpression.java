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
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
