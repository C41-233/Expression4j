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
        throw Error.badOperator();
    }
}
