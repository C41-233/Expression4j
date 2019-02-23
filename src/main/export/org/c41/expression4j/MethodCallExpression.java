package org.c41.expression4j;

import java.lang.reflect.Method;

public class MethodCallExpression extends CallExpression{

    private final Expression self;

    MethodCallExpression(Expression self, Method method, Expression[] parameters) {
        super(method, parameters);
        this.self = self;
    }

    @Override
    void emit(BodyEmit ctx) {
        self.emit(ctx);
        pushParameters(ctx);
        ctx.invokevirtual((Method) method);
    }

    @Override
    public Class<?> getExpressionType() {
        return ((Method)method).getReturnType();
    }

}
