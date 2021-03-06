package org.c41.expression4j;

public class IntConstantExpression extends IntegerConstantExpression{

    IntConstantExpression(int value) {
        super(value);
    }

    @Override
    public Class<?> getExpressionType() {
        return int.class;
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        sb.append(String.valueOf(value));
    }
}
