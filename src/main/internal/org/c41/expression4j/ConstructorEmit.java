package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

final class ConstructorEmit extends MethodEmit{

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
        invokespecial("java/lang/Object", "<init>", "()V");
        ret();
    }
}
