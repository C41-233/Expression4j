package org.c41.expression4j;
import jdk.internal.org.objectweb.asm.Type;

import java.lang.reflect.Field;

public abstract class FieldExpression extends Expression{

    final Field field;

    FieldExpression(Field field){
        this.field = field;
    }

    abstract void emitAssign(BodyEmit ctx, Expression right);

    @Override
    public Class<?> getExpressionType() {
        return field.getType();
    }
}

final class MemberFieldExpression extends FieldExpression{

    private final Expression self;

    MemberFieldExpression(Expression self, Field field){
        super(field);
        this.self = self;
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
}

final class StaticFieldExpression extends Expression{

    private final Field field;

    StaticFieldExpression(Field field){
        this.field = field;
    }

    @Override
    void emit(BodyEmit ctx) {
        ctx.getstatic(
            Type.getInternalName(field.getDeclaringClass()),
            field.getName(),
            Type.getDescriptor(field.getType())
        );
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }

    void emitAssign(BodyEmit ctx, Expression expression) {

    }
}
