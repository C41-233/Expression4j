package org.c41.expression4j;

public class IntConstantExpression extends Expression{

    private final int value;

    IntConstantExpression(int value){
        this.value = value;
    }

    @Override
    void emit(BodyEmit ctx) {
        switch (value){
            case 0 : ctx.iconst_0(); break;
            case 1 : ctx.iconst_1(); break;
            case 2 : ctx.iconst_2(); break;
            case 3 : ctx.iconst_3(); break;
            case 4 : ctx.iconst_4(); break;
            case 5 : ctx.iconst_5(); break;
            default: ctx.ldc(value); break;
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return int.class;
    }
}
