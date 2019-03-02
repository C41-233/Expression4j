package org.c41.expression4j;

import org.objectweb.asm.Label;

public abstract class Expression {

    void emitRead(BodyEmit ctx){
        throw Error.badOperator();
    }

    void emitBalance(BodyEmit ctx) { throw Error.badOperator(); }

    void emitWrite(BodyEmit ctx, Expression value, boolean isBalance){ throw Error.writeValueExpression();}

    void emitJmpIf(BodyEmit ctx, Label label){ throw Error.badOperator(); }

    void emitJmpIfNot(BodyEmit ctx, Label label){ throw Error.badOperator(); }

    public abstract Class<?> getExpressionType();

}
