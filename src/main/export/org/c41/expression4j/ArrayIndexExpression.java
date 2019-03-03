package org.c41.expression4j;

public class ArrayIndexExpression extends Expression{

    private Expression array;
    private Expression index;

    ArrayIndexExpression(Expression array, Expression index){
        this.array = array;
        this.index = index;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        array.emitRead(ctx);
        index.emitRead(ctx);
        //todo
    }

    @Override
    public void emitWrite(BodyEmit ctx, Expression expression, boolean isBalance) {
        array.emitRead(ctx);
        index.emitRead(ctx);
        expression.emitRead(ctx);
        if(!isBalance){
            ctx.dup_x2(expression.getExpressionType());
        }
        ctx.astore(getExpressionType());
    }

    @Override
    public Class<?> getExpressionType() {
        return array.getExpressionType().getComponentType();
    }

    @Override
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
