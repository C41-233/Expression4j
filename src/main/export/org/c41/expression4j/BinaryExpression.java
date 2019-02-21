package org.c41.expression4j;

public abstract class BinaryExpression extends Expression{

    private final Expression left;
    private final Expression right;

    private final Expression liftLeft;
    private final Expression liftRight;

    BinaryExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;

        StackType leftType = TypeUtils.getStackType(left.getExpressionType());
        StackType rightType = TypeUtils.getStackType(right.getExpressionType());

        if(leftType == StackType.Long && rightType == StackType.Int){
            this.liftLeft = left;
            this.liftRight = new CastExpression(right, long.class);
        }
        else if(rightType == StackType.Long && leftType == StackType.Int){
            this.liftLeft = new CastExpression(left, long.class);
            this.liftRight = right;
        }
        else{
            this.liftLeft = left;
            this.liftRight = right;
        }

    }

    @Override
    void emit(BodyEmit ctx) {
        liftLeft.emit(ctx);
        liftRight.emit(ctx);
        emitOpCode(ctx);
    }

    abstract void emitOpCode(BodyEmit ctx);

    @Override
    public Class<?> getExpressionType() {
        return liftLeft.getExpressionType();
    }

}
