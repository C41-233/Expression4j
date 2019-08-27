package org.c41.expression4j;

import org.objectweb.asm.Label;

public class ForExpression extends Expression{

    private final TargetLabel label;
    private final Expression expression1;
    private final Expression expression2;
    private final Expression expression3;
    private final Expression[] bodyExpressions;

    ForExpression(TargetLabel label, Expression expression1, Expression expression2, Expression expression3, Expression[] bodyExpression){
        this.label = label;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.bodyExpressions = bodyExpression;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        Label breakTarget = new Label();

        if(label != null){
            ctx.label(label.Target);
        }

        Label loopStart = new Label();
        Label loopEnd = new Label();

        ctx.JmpTargetControlFlow.pushJmpTarget(breakTarget, null);
        ctx.ParameterStack.pushScope();
        {
            expression1.emitBalance(ctx);
            ctx.label(loopStart);
            ctx.ParameterStack.pushScope();
            {
                expression2.emitJmpIfNot(ctx, loopEnd);
            }
            ctx.ParameterStack.popScope();
            ctx.ParameterStack.pushScope();
            {
                for (Expression body : bodyExpressions){
                    body.emitBalance(ctx);
                }
                ctx.ParameterStack.pushScope();
                {
                    expression3.emitBalance(ctx);
                }
                ctx.ParameterStack.popScope();
            }
            ctx.ParameterStack.popScope();
            ctx.jmp(loopStart);
            ctx.label(loopEnd);
        }
        ctx.ParameterStack.popScope();
        ctx.label(breakTarget);
        ctx.JmpTargetControlFlow.popJmpTarget();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        sb.append("for(");
        expression1.toString(sb, CodeStyle.None);
        sb.append(";");
        expression2.toString(sb, CodeStyle.None);
        sb.append(";");
        expression3.toString(sb, CodeStyle.None);
        sb.append(")");
        sb.appendLine("{");
        sb.pushIndent();
        for(Expression expression : bodyExpressions){
            expression.toString(sb, CodeStyle.AlreadyBlock);
        }
        sb.popIndent();
        sb.appendLine();
    }
}
