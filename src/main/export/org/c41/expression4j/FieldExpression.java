package org.c41.expression4j;

import jdk.internal.org.objectweb.asm.Type;
import org.c41.expression4j.annotations.ValueExpression;

import java.lang.reflect.Field;

public class FieldExpression extends Expression implements ValueExpression {

    private final Expression self;
    private final Field field;

    FieldExpression(Expression self, Field field){
        this.self = self;
        this.field = field;
    }

    @Override
    void emit(BodyEmit emit, EmitType access) {
        self.emit(emit, EmitType.Read);
        if(access == EmitType.Read){
            emit.getfield(
                Type.getInternalName(field.getDeclaringClass()),
                field.getName(),
                Type.getDescriptor(field.getType())
            );
        }
    }

}
