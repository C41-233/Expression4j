package org.c41.expression4j;

import org.objectweb.asm.Label;

public class BreakExpression extends Expression{

    private final TargetLabel label;

    BreakExpression(){
        this.label = null;
    }

    BreakExpression(TargetLabel label){
        this.label = label;
    }

    public TargetLabel getTarget(){
        return this.label;
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    @Override
    void emitBalance(BodyEmit ctx) {
        Label target;
        if(this.label == null){
            target = ctx.JmpTargetControlFlow.getBreakTarget();
        }
        else{
            target = label.Target;
        }

        Label jmp = ctx.RedirectReturnControlFlow.redirectLabel(target);
        if(jmp != null){
            ctx.jmp(jmp);
        }
        else{
            ctx.jmp(target);
        }
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        sb.appendLine("break;");
    }
}
