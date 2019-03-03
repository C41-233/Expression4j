package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

final class ClassEmit{

    private static final Method DefineClass;
    private static AtomicLong NameHash = new AtomicLong();

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

    public static <T> T emit(Class<T> proxy, ClassLoader cl, Expression body, ParameterExpression[] parameters){
        String name = "$" + String.valueOf(NameHash.incrementAndGet());

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        StringWriter debug = new StringWriter();
        PrintWriter printWriter = new PrintWriter(debug, true);
        ClassVisitor visitor = new TraceClassVisitor(writer, printWriter);

        //todo
        Method method = proxy.getDeclaredMethods()[0];

        Class<?>[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length != parameters.length){
            throw Error.parametersNotMatch(parameterTypes, parameters);
        }
        for(int i=0; i<parameters.length; i++){
            Class<?> methodParameter = parameterTypes[i];
            Class<?> inParameter = parameters[i].getExpressionType();
            if(!methodParameter.isAssignableFrom(inParameter)){
                throw Error.parametersNotMatch(parameterTypes, parameters);
            }
        }

        visitor.visit(
                Opcodes.V1_8,
                Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
                Type.getInternalName(proxy) + name,
                null,
                "java/lang/Object",
                new String[]{Type.getInternalName(proxy)}
        );

        ConstructorEmit constructor = new ConstructorEmit(visitor);
        constructor.emit();

        BodyEmit bodyEmit = new BodyEmit(
            visitor,
            method,
            body,
            parameters
        );
        bodyEmit.emit();

        visitor.visitEnd();

        ToStringEmit toStringEmit = new ToStringEmit(visitor, bodyEmit.toString());
        toStringEmit.emit();

        byte[] bs = writer.toByteArray();
        try {
            return emit(cl, proxy.getTypeName() + name, bs);
        } catch (Throwable e) {
            throw Error.emitFail(debug.getBuffer().toString(), e);
        }
    }

}
