package org.c41.expression4j;

import org.objectweb.asm.Label;

public class GreaterExpression extends Expression {

    private final BinaryLift lift;

    GreaterExpression(Expression left, Expression right) {
        this.lift = new BinaryLift(left, right);
    }

    @Override
    void emitJmpIf(BodyEmit ctx, Label label) {
        if(lift.LiftType == int.class){
            ctx.if_icmpgt(label);
            return;
        }
        throw CompileException.badOperator();
    }

    @Override
    void emitJmpIfNot(BodyEmit ctx, Label label) {
        if(lift.LiftType == int.class){
            lift.LiftLeft.emitRead(ctx);
            lift.LiftRight.emitRead(ctx);
            ctx.if_icmple(label);
            return;
        }
        throw CompileException.badOperator();
    }

    @Override
    public Class<?> getExpressionType() {
        return boolean.class;
    }
}
