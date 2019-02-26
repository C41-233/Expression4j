package org.c41.expression4j;

public abstract class IntegerConstantExpression extends Expression{

    private final int value;

    IntegerConstantExpression(int value){

        this.value = value;
    }

    @Override
    final void emitRead(BodyEmit ctx) {
        switch (value){
            case -1 : ctx.iconst_m1(); return;
            case 0 : ctx.iconst_0(); return;
            case 1 : ctx.iconst_1(); return;
            case 2 : ctx.iconst_2(); return;
            case 3 : ctx.iconst_3(); return;
            case 4 : ctx.iconst_4(); return;
            case 5 : ctx.iconst_5(); return;
        }
        if(value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE){
            ctx.bipush(value);
            return;
        }
        if(value >= Short.MIN_VALUE && value <= Short.MAX_VALUE){
            ctx.sipush(value);
            return;
        }
        ctx.ldc(value);
    }

    @Override
    public abstract Class<?> getExpressionType();
}
