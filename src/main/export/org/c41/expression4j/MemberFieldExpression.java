package org.c41.expression4j;

import jdk.internal.org.objectweb.asm.Type;

import java.lang.reflect.Field;

public class MemberFieldExpression extends FieldExpression{

    private final Expression self;

    MemberFieldExpression(Expression self, Field field){
        super(field);
        this.self = self;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        self.emitRead(ctx);

        ctx.getfield(
            Type.getInternalName(field.getDeclaringClass()),
            field.getName(),
            Type.getDescriptor(field.getType())
        );
    }

    @Override
    void emitWrite(BodyEmit ctx, Expression right, boolean balance){
        self.emitRead(ctx);
        right.emitRead(ctx);
        if (!balance) {
            ctx.dup_x1(right.getExpressionType());
        }
        ctx.putfield(
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
    void toString(ClassStringBuilder sb) {
        throw Error.badOperator();
    }
}
