package org.c41.expression4j;

import java.util.concurrent.atomic.AtomicLong;

public class ParameterExpression extends Expression{

    private static final AtomicLong Value = new AtomicLong();

    private final Class<?> type;
    private final String name;

    ParameterExpression(Class<?> type, String name){
        this.type = type;
        this.name = name;
    }

    ParameterExpression(Class<?> type){
        this(type, "local$" + Value.incrementAndGet());
    }

    @Override
    void emitRead(BodyEmit ctx) {
        ctx.load(this);
    }

    @Override
    void emitWrite(BodyEmit ctx, Expression expression, boolean isBalance) {
        ctx.ParameterStack.declareParameter(this);
        expression.emitRead(ctx);
        if(!isBalance){
            ctx.dup(expression.getExpressionType());
        }
        ctx.store(this);
    }

    @Override
    public Class<?> getExpressionType() {
        return type;
    }

    public String getName(){
        return this.name;
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        if(CodeStyle.is(mask, CodeStyle.Declare)){
            sb.append(type.getSimpleName() + " ");
        }
        sb.append(name);
    }
}
