package org.c41.expression4j;

import org.objectweb.asm.Label;

public class ForExpression extends Expression{

    private final Expression expression1;
    private final Expression expression2;
    private final Expression expression3;
    private final Expression[] bodyExpressions;

    ForExpression(Expression expression1, Expression expression2, Expression expression3, Expression[] bodyExpression){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.bodyExpressions = bodyExpression;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        Label loopStart = new Label();
        Label loopEnd = new Label();
        ctx.pushScope();
        {
            expression1.emitBalance(ctx);
            ctx.label(loopStart);
            ctx.pushScope();
            {
                expression2.emitJmpIfNot(ctx, loopEnd);
            }
            ctx.popScope();
            ctx.pushScope();
            {
                for (Expression body : bodyExpressions){
                    body.emitBalance(ctx);
                }
                ctx.pushScope();
                {
                    expression3.emitBalance(ctx);
                }
                ctx.popScope();
            }
            ctx.popScope();
            ctx.jmp(loopStart);
            ctx.label(loopEnd);
        }
        ctx.popScope();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

}
