package org.c41.expression4j;

public class NewArrayExpression extends Expression{

    private final Class<?> elementType;
    private final Expression length;

    NewArrayExpression(Class<?> elementType, Expression length){
        this.elementType = elementType;
        this.length = length;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        length.emitRead(ctx);
        ctx.newarray(elementType);
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
