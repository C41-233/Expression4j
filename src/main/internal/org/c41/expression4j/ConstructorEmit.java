package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Constructor;

final class ConstructorEmit extends MethodEmit{

    private static final Constructor<Object> ObjectConstructor;

    static{
        try {
            ObjectConstructor = Object.class.getConstructor();
        } catch (NoSuchMethodException e) {
            throw Error.emitFail("cannot find constructor", e);
        }
    }

    protected ConstructorEmit(ClassVisitor generator) {
        super(generator.visitMethod(
            Opcodes.ACC_PUBLIC,
            "<init>",
            "()V",
            null,
            null
        ));
    }

    @Override
    public void emitCodes() {
        aload(0);
        invokespecial(ObjectConstructor);
        ret();
    }
}
