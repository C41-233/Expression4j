package org.c41.expression4j;

import org.c41.expression4j.annotations.ValueExpression;

public class AddExpression extends BinaryExpression implements ValueExpression {

    AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    void emitOpCode(BodyEmit ctx) {
        ctx.iadd();
    }
}
