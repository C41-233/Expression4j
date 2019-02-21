package org.c41.expression4j;
import jdk.internal.org.objectweb.asm.Type;

import java.lang.reflect.Field;

public class FieldExpression extends Expression{

    private final Expression self;
    private final Field field;

    FieldExpression(Expression self, Field field){
        this.self = self;
        this.field = field;
    }

    @Override
    void emit(BodyEmit ctx) {
        self.emit(ctx);

        ctx.getfield(
            Type.getInternalName(field.getDeclaringClass()),
            field.getName(),
            Type.getDescriptor(field.getType())
        );
    }

    void emitAssign(BodyEmit ctx, Expression right){
        self.emit(ctx);
        right.emit(ctx);
        ctx.putField(
            Type.getInternalName(field.getDeclaringClass()),
            field.getName(),
            Type.getDescriptor(field.getType())
        );
    }

    @Override
    public Class<?> getExpressionType() {
        return field.getType();
    }
}
