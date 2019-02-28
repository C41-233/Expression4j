package org.c41.expression4j;

import org.objectweb.asm.Label;

public abstract class Expression {

    void emitRead(BodyEmit ctx){
        throw CompileException.badOperator();
    }

    void emitBalance(BodyEmit ctx) { throw CompileException.badOperator(); }

    void emitWrite(BodyEmit ctx, Expression value, boolean isBalance){ throw CompileException.writeValueExpression();}

    void emitJmpIf(BodyEmit ctx, Label label){ throw CompileException.badOperator(); }

    void emitJmpIfNot(BodyEmit ctx, Label label){ throw CompileException.badOperator(); }

    public abstract Class<?> getExpressionType();

}
