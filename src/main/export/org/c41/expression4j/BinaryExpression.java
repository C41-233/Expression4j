package org.c41.expression4j;

public abstract class BinaryExpression extends Expression{

    private final Expression left;
    private final Expression right;

    BinaryExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    void emit(BodyEmit ctx) {
        left.emit(ctx);
        right.emit(ctx);
        emitOpCode(ctx);
    }

    abstract void emitOpCode(BodyEmit ctx);

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
