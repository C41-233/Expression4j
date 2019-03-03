package org.c41.expression4j;

public class BlockExpression extends Expression{

    private Expression[] expressions;

    BlockExpression(Expression[] expressions) {
        this.expressions = expressions;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        ctx.ParameterStack.pushScope();
        for (int i = 0; i < expressions.length; i++) {
            Expression expression = expressions[i];
            expression.emitBalance(ctx);
        }
        ctx.ParameterStack.popScope();
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        if(!CodeStyle.is(mask, CodeStyle.AlreadyBlock)){
            sb.append('{');
            sb.appendLine();
            sb.pushIndent();
        }
        for (int i = 0; i < expressions.length; i++) {
            Expression expression = expressions[i];
            expression.toString(sb, 0);
            sb.append(';');
            if(i != expressions.length - 1){
                sb.appendLine();
            }
        }
        if(!CodeStyle.is(mask, CodeStyle.AlreadyBlock)){
            sb.popIndent();
            sb.append('}');
        }
    }
}
