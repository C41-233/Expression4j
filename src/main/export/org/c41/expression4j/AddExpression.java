package org.c41.expression4j;

public class AddExpression extends BinaryExpression{

    AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    void emitOpCode(BodyEmit ctx) {
        ctx.iadd();
    }
}
