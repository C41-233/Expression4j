package org.c41.expression4j;
import java.lang.reflect.Field;

public abstract class FieldExpression extends Expression{

    final Field field;

    FieldExpression(Field field){
        this.field = field;
    }

    abstract void emitWrite(BodyEmit ctx, Expression right, boolean balance);

    @Override
    abstract void emitRead(BodyEmit bodyEmit);

    @Override
    public Class<?> getExpressionType() {
        return field.getType();
    }
}

