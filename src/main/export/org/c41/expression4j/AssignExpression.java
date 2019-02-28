package org.c41.expression4j;

public class AssignExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final Expression liftRight;

    AssignExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;

        if(left.getExpressionType() != right.getExpressionType()){
            this.liftRight = new CastExpression(right, left.getExpressionType());
        }
        else{
            this.liftRight = right;
        }
    }

    @Override
    void emitRead(BodyEmit ctx) {
        left.emitWrite(ctx, liftRight, false);
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        left.emitWrite(ctx, liftRight, true);
    }

    @Override
    public Class<?> getExpressionType() {
        return left.getExpressionType();
    }
}
