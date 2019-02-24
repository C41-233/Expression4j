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
        Label label = ctx.RedirectReturnControlFlow.getCurrentRedirectTarget();
        if(expression != null){
            expression.emitRead(ctx);
            if(label != null){
                ctx.jmp(label);
            }
            else{
                ctx.ret(expression.getExpressionType());
            }
        }
        else{
            if(label != null){
                ctx.jmp(label);
            }
            else {
                ctx.ret();
            }
        }

        if(label != null){
            ctx.RedirectReturnControlFlow.trigger();
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
