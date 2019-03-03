package org.c41.expression4j;

import org.objectweb.asm.Label;

public class ReturnExpression extends Expression{

    private final Expression expression;

    ReturnExpression(){
        this(null);
    }

    ReturnExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        Label jmp = ctx.RedirectReturnControlFlow.redirectReturn();
        if(expression != null){
            returnValue(ctx, jmp);
        }
        else{
            returnVoid(ctx, jmp);
        }
    }

    private void returnValue(BodyEmit ctx, Label jmp){
        expression.emitRead(ctx);
        if(jmp != null){
            ctx.jmp(jmp);
        }
        else{
            ctx.ret(expression.getExpressionType());
        }
    }

    private void returnVoid(BodyEmit ctx, Label jmp){
        if(jmp != null){
            ctx.jmp(jmp);
        }
        else {
            ctx.ret();
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    @Override
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
