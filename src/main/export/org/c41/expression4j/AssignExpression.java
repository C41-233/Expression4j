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
    void emit(BodyEmit ctx) {
        if(left instanceof FieldExpression){
            FieldExpression e = (FieldExpression)left;
            e.emitAssign(ctx, liftRight);
        }
        else if(left instanceof ParameterExpression){
            ParameterExpression e = (ParameterExpression)left;
            ctx.declareParameter(e);
            e.emitAssign(ctx, liftRight);
        }
        else if(left instanceof ArrayIndexExpression){
            ArrayIndexExpression e = (ArrayIndexExpression)left;
            e.emitAssign(ctx, liftRight);
        }
        else{
            throw CompileExpression.writeValueExpression();
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return left.getExpressionType();
    }
}
