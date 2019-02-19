package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

final class BodyEmit extends MethodEmit {

    private final Expression body;
    private final Parameter[] parameters;
    private final Class<?> returnType;

    protected BodyEmit(ClassVisitor writer, Method method, Expression body, Parameter[] parameters) {
        super(writer.visitMethod(
            Opcodes.ACC_PUBLIC,
            method.getName(),
            Type.getMethodDescriptor(method),
            null,
            null
        ));
        this.body = body;
        this.parameters = parameters;
        this.returnType = method.getReturnType();
    }

    @Override
    protected void emitCodes() {
        for(int i=0; i<parameters.length; i++){
            parameters[i].slot = i + 1;
        }
        body.emit(this, EmitType.Read);
        tret(returnType);
    }
}
