package org.c41.expression4j;

public class CastExpression extends Expression{

    private final Expression expression;
    private final Class<?> type;

    CastExpression(Expression expression, Class<?> type){
        this.expression = expression;
        this.type = type;
    }

    @Override
    void emit(BodyEmit ctx) {
        expression.emit(ctx);
        StackType leftType = TypeUtils.getStackType(expression.getExpressionType());
        if(type == long.class){
            if(leftType == StackType.Int){
                ctx.i2l();
                return;
            }
        }
        else if(type == int.class){
            if(leftType == StackType.Long){
                ctx.l2i();
                return;
            }
        }
        throw CompileExpression.badOperator();
    }

    @Override
    public Class<?> getExpressionType() {
        return type;
    }
}
