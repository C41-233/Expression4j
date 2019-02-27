package org.c41.expression4j;

import org.objectweb.asm.Label;

public class GreaterExpression extends Expression {

    private final BinaryLift lift;

    GreaterExpression(Expression left, Expression right) {
        this.lift = new BinaryLift(left, right);
    }

    @Override
    void emitJmpIfNot(BodyEmit ctx, Label label) {
        ctx.if_icmple(label);
    }

    @Override
    public Class<?> getExpressionType() {
        return boolean.class;
    }
}
