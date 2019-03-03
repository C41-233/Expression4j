package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

final class ToStringEmit extends MethodEmit{

    private static final Method ToStringMethod;

    static {
        try {
            ToStringMethod = Object.class.getMethod("toString");
        } catch (NoSuchMethodException e) {
            throw Error.emitFail("method not found", e);
        }
    }

    private final String value;

    ToStringEmit(ClassVisitor visitor, String value) {
        super(visitor.visitMethod(
            Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
            ToStringMethod.getName(),
            Type.getMethodDescriptor(ToStringMethod),
            null,
            null
        ));
        this.value = value;
    }

    @Override
    protected void emitCodes() {
        ldc(value);
        areturn();
    }

}
