package org.c41.expression4j;

import org.c41.expression4j.annotations.ValueExpression;

public class Parameter extends Expression implements ValueExpression {

    int slot = -1;

    private final Class<?> type;

    Parameter(Class<?> type){
        this.type = type;
    }

    @Override
    void emit(BodyEmit emit, EmitType access) {
        if(access == EmitType.Read){
            emit.tload(slot, type);
        }
    }
}
