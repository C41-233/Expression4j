package org.c41.expression4j;

public class StringConstantExpression extends Expression{

    private final String value;

    StringConstantExpression(String value){
        this.value = value;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        ctx.ldc(value);
    }

    @Override
    public Class<?> getExpressionType() {
        return String.class;
    }

    @Override
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
