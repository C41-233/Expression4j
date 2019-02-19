package org.c41.expression4j;

import org.c41.expression4j.annotations.ValueExpression;

public abstract class BinaryExpression extends Expression implements ValueExpression {

    private final Expression left;
    private final Expression right;

    BinaryExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    void emit(BodyEmit ctx, EmitType access) {
        if(access != EmitType.Read){
            throw new CompileExpression();
        }
        left.emit(ctx, EmitType.Read);
        right.emit(ctx, EmitType.Read);
        emitOpCode(ctx);
    }

    abstract void emitOpCode(BodyEmit ctx);
}
