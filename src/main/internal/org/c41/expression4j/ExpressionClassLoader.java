package org.c41.expression4j;

final class ExpressionClassLoader extends ClassLoader{

    public Class<?> emit(String name, byte[] bs){
        return defineClass(name, bs, 0, bs.length);
    }

}
