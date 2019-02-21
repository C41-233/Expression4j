package org.c41.expression4j;

public class AssignExpression extends Expression {

    private final Expression left;
    private final Expression right;

    AssignExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    void emit(BodyEmit ctx) {
        if(left instanceof FieldExpression){
            FieldExpression e = (FieldExpression)left;
            e.emitAssign(ctx, right);
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return left.getExpressionType();
    }
}
