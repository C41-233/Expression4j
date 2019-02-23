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

    public void emitWrite(BodyEmit ctx, Expression expression) {
        array.emitRead(ctx);
        index.emitRead(ctx);
        expression.emitRead(ctx);
        ctx.astore(getExpressionType());
    }

    @Override
    public Class<?> getExpressionType() {
        return array.getExpressionType().getComponentType();
    }

}
