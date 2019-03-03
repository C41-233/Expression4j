package org.c41.expression4j;

public class SubtractExpression extends Expression{

    private final BinaryLift lift;

    SubtractExpression(Expression left, Expression right) {
        this.lift = new BinaryLift(left, right);
    }

    @Override
    void emitRead(BodyEmit ctx) {
        lift.LiftLeft.emitRead(ctx);
        lift.LiftRight.emitRead(ctx);
        ctx.sub(lift.LiftType);
    }

    @Override
    public Class<?> getExpressionType() {
        return lift.LiftType;
    }

    @Override
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
