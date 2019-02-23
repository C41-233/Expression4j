package org.c41.expression4j;

public class ArrayIndexExpression extends Expression{

    private Expression array;
    private Expression index;

    ArrayIndexExpression(Expression array, Expression index){
        this.array = array;
        this.index = index;
    }

    @Override
    void emit(BodyEmit ctx) {
        array.emit(ctx);
        index.emit(ctx);

    }

    public void emitAssign(BodyEmit ctx, Expression expression) {
        array.emit(ctx);
        index.emit(ctx);
        expression.emit(ctx);
        ctx.astore(getExpressionType());
    }

    @Override
    public Class<?> getExpressionType() {
        return array.getExpressionType().getComponentType();
    }

}
