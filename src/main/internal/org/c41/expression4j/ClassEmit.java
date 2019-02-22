package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

final class ClassEmit<T> {

    private static final Method DefineClass;
    private static final StringWriter debug = new StringWriter();

    static {
        try {
            DefineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            DefineClass.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T emit(ClassLoader cl, String name, byte[] bs) throws Exception{
        Class<?> cc = (Class<?>) DefineClass.invoke(cl, name, bs, 0 , bs.length);
        return (T)cc.newInstance();
    }

    private final String name;
    private final ClassWriter writer;
    private final ClassVisitor visitor;
    private final Class<T> proxy;

    public ClassEmit(Class<T> proxy){
        this.proxy = proxy;
        name = "$" + String.valueOf(System.currentTimeMillis() + hashCode());

        writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        PrintWriter printWriter = new PrintWriter(debug, true);
        visitor = new TraceClassVisitor(this.writer, printWriter);

        visitor.visit(
            Opcodes.V1_8,
            Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
            Type.getInternalName(proxy) + name,
            null,
            "java/lang/Object",
            new String[]{Type.getInternalName(proxy)}
        );
    }

    protected T emit(ClassLoader cl, Expression body, ParameterExpression[] parameters){
        ConstructorEmit constructor = new ConstructorEmit(visitor);
        constructor.emit();

        //todo
        Method method = proxy.getDeclaredMethods()[0];

        Class<?>[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length != parameters.length){
            throw CompileException.parametersNotMatch(parameterTypes, parameters);
        }
        for(int i=0; i<parameters.length; i++){
            if(parameterTypes[i] != parameters[i].getExpressionType()){
                throw CompileException.parametersNotMatch(parameterTypes, parameters);
            }
        }

        BodyEmit bodyEmit = new BodyEmit(
            visitor,
            method,
            body,
            parameters
        );
        bodyEmit.emit();

        visitor.visitEnd();

        byte[] bs = writer.toByteArray();
        try {
            return emit(cl, proxy.getTypeName() + name, bs);
        } catch (Throwable e) {
            throw CompileException.emitFail(debug.getBuffer().toString(), e);
        }
    }

}
