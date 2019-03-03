package org.c41.expression4j;

public class AddExpression extends Expression{

    private final BinaryLift lift;

    AddExpression(Expression left, Expression right) {
        this.lift = new BinaryLift(left, right);
    }

    @Override
    void emitRead(BodyEmit ctx) {
        lift.LiftLeft.emitRead(ctx);
        lift.LiftRight.emitRead(ctx);
        ctx.add(lift.LiftType);
    }

    @Override
    public Class<?> getExpressionType() {
        return lift.LiftType;
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        if(CodeStyle.is(mask, CodeStyle.Advance)){
            sb.append('(');
        }
        lift.Left.toString(sb, CodeStyle.Advance);
        sb.append(" + ");
        lift.Right.toString(sb, CodeStyle.Advance);
        if(CodeStyle.is(mask, CodeStyle.Advance)){
            sb.append(')');
        }
    }
}
