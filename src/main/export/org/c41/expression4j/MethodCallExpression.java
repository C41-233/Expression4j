package org.c41.expression4j;

import java.lang.reflect.Method;

public class MethodCallExpression extends CallExpression{

    private final Expression self;

    MethodCallExpression(Expression self, Method method, Expression[] parameters) {
        super(method, parameters);
        this.self = self;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        self.emitRead(ctx);
        pushParameters(ctx);
        ctx.invokevirtual((Method) method);
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
    }

    @Override
    public Class<?> getReturnType() {
        return ((Method)method).getReturnType();
    }

}
