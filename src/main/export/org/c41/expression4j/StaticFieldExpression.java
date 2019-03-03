package org.c41.expression4j;

import org.objectweb.asm.Type;

import java.lang.reflect.Field;

public class StaticFieldExpression extends FieldExpression{

    StaticFieldExpression(Field field){
        super(field);
    }

    @Override
    void emitRead(BodyEmit ctx) {
        ctx.getstatic(
            Type.getInternalName(field.getDeclaringClass()),
            field.getName(),
            Type.getDescriptor(field.getType())
        );
    }

    @Override
    public Class<?> getExpressionType() {
        return field.getType();
    }

    @Override
    void emitWrite(BodyEmit ctx, Expression expression, boolean balance) {
        expression.emitRead(ctx);
        //todo
    }

    @Override
    void toString(ClassStringBuilder sb, int mask) {
        throw Error.badOperator();
    }
}
