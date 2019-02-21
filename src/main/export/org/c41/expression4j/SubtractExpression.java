package org.c41.expression4j;

public class SubtractExpression extends BinaryExpression{

    SubtractExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    void emitOpCode(BodyEmit ctx) {
        ctx.tsub(getExpressionType());
    }
}
