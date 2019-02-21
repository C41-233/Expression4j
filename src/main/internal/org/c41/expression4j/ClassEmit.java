package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import java.lang.reflect.Method;

final class ClassEmit<T> {

    private static final String InternalNameBase = "org/c41/expression4j/IMPL";
    private static final String ClassNameBase = "org.c41.expression4j.IMPL";

    private final String name;
    private final ClassWriter writer;
    private final ClassVisitor visitor;
    private final Class<T> proxy;

    public ClassEmit(Class<T> proxy){
        this.proxy = proxy;
        name = String.valueOf(System.currentTimeMillis());

        writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        //visitor = new TraceClassVisitor(writer, new PrintWriter(System.out));
        visitor = writer;
        visitor.visit(
                Opcodes.V1_8,
                Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
                InternalNameBase + name,
                null,
                "java/lang/Object",
                new String[]{Type.getInternalName(proxy)}
        );
    }

    protected T emit(Expression body, ParameterExpression[] parameters){
        parameters = parameters.clone();

        ConstructorEmit constructor = new ConstructorEmit(visitor);
        constructor.emit();

        //todo
        Method method = proxy.getDeclaredMethods()[0];

        BodyEmit bodyEmit = new BodyEmit(
            visitor,
            method,
            body,
            parameters
        );
        bodyEmit.emit();

        visitor.visitEnd();

        byte[] bs = writer.toByteArray();

        ExpressionClassLoader cl = new ExpressionClassLoader();
        try {
            return (T) cl.emit(ClassNameBase + name, bs).newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

}
