package org.c41.expression4j;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

abstract class MethodEmit {

    private MethodVisitor visitor;

    MethodEmit(MethodVisitor visitor){
        this.visitor = visitor;
    }

    protected void emit(){
        visitor.visitCode();
        emitCodes();
        visitor.visitMaxs(0, 0);
        visitor.visitEnd();
    }

    protected abstract void emitCodes();

    public final void aload(int n){
        visitor.visitVarInsn(Opcodes.ALOAD, n);
    }

    public final void iload(int n){
        visitor.visitVarInsn(Opcodes.ILOAD, n);
    }

    public final void lload(int n){
        visitor.visitVarInsn(Opcodes.LLOAD, n);
    }

    public final void fload(int n){
        visitor.visitVarInsn(Opcodes.FLOAD, n);
    }

    public final void dload(int n){
        visitor.visitVarInsn(Opcodes.DLOAD, n);
    }

    public final void tload(int n, Class<?> type){
        switch (TypeUtils.getStackType(type)){
            case Int:
                iload(n);
                break;
            case Long:
                lload(n);
                break;
            case Float:
                fload(n);
                break;
            case Double:
                dload(n);
                break;
            case Reference:
                aload(n);
                break;
        }
    }

    public final void istore(int n){
        visitor.visitVarInsn(Opcodes.ISTORE, n);
    }

    public final void lstore(int n){
        visitor.visitVarInsn(Opcodes.LSTORE, n);
    }

    public final void fstore(int n){
        visitor.visitVarInsn(Opcodes.FSTORE, n);
    }

    public final void dstore(int n){
        visitor.visitVarInsn(Opcodes.DSTORE, n);
    }

    public final void astore(int n){
        visitor.visitVarInsn(Opcodes.ASTORE, n);
    }
    public final void tstore(int n, Class<?> type){
        switch (TypeUtils.getStackType(type)){
            case Int:
                istore(n);
                break;
            case Long:
                lstore(n);
                break;
            case Float:
                fstore(n);
                break;
            case Double:
                dstore(n);
                break;
            case Reference:
                astore(n);
                break;
        }
    }

    public final void iadd(){
        visitor.visitInsn(Opcodes.IADD);
    }

    public final void ladd(){
        visitor.visitInsn(Opcodes.LADD);
    }

    public final void fadd(){
        visitor.visitInsn(Opcodes.FADD);
    }

    public final void dadd(){
        visitor.visitInsn(Opcodes.DADD);
    }

    public final void tadd(Class<?> type){
        switch (TypeUtils.getStackType(type)){
            case Int:
                iadd();
                break;
            case Long:
                ladd();
                break;
            case Float:
                fadd();
                break;
            case Double:
                dadd();
                break;
            case Reference:
                throw CompileException.badOperator();
        }
    }

    public final void isub(){
        visitor.visitInsn(Opcodes.ISUB);
    }

    public final void lsub(){
        visitor.visitInsn(Opcodes.LSUB);
    }

    public final void tsub(Class<?> type){
        switch (TypeUtils.getStackType(type)){
            case Int:
                isub();
                break;
            case Long:
                lsub();
                break;
            case Float:
                break;
            case Double:
                break;
            case Reference:
                throw CompileException.badOperator();
        }
    }

    public final void ret(){
        visitor.visitInsn(Opcodes.RETURN);
    }

    public final void ireturn() {
        visitor.visitInsn(Opcodes.IRETURN);
    }

    public final void lreturn(){
        visitor.visitInsn(Opcodes.LRETURN);
    }

    public final void freturn(){
        visitor.visitInsn(Opcodes.FRETURN);
    }

    public final void dreturn(){
        visitor.visitInsn(Opcodes.DRETURN);
    }

    public final void areturn(){
        visitor.visitInsn(Opcodes.ARETURN);
    }

    final void treturn(Class<?> type){
        if(type == null || type == void.class){
            ret();
            return;
        }
        switch (TypeUtils.getStackType(type)){
            case Int:
                ireturn();
                break;
            case Long:
                lreturn();
                break;
            case Float:
                freturn();
                break;
            case Double:
                dreturn();
                break;
            case Reference:
                areturn();
                break;
        }
    }

    public final void getfield(String owner, String name, String descriptor){
        visitor.visitFieldInsn(Opcodes.GETFIELD,owner, name, descriptor);
    }

    public final void putfield(String owner, String name, String descriptor){
       visitor.visitFieldInsn(Opcodes.PUTFIELD, owner, name, descriptor);
    }

    public final void getstatic(String owner, String name, String descriptor){
        visitor.visitFieldInsn(Opcodes.GETSTATIC, owner, name, descriptor);
    }

    public final void i2l(){
        visitor.visitInsn(Opcodes.I2L);
    }

    public final void l2i(){
        visitor.visitInsn(Opcodes.L2I);
    }

    public final void iconst_0(){
        visitor.visitInsn(Opcodes.ICONST_0);
    }

    public final void iconst_1(){
        visitor.visitInsn(Opcodes.ICONST_1);
    }

    public final void iconst_2(){
        visitor.visitInsn(Opcodes.ICONST_2);
    }

    public final void iconst_3(){
        visitor.visitInsn(Opcodes.ICONST_3);
    }

    public final void iconst_4(){
        visitor.visitInsn(Opcodes.ICONST_4);
    }

    public final void iconst_5(){
        visitor.visitInsn(Opcodes.ICONST_5);
    }

    public final void ldc(Object value){
        visitor.visitLdcInsn(value);
    }

    public final void invokespecial(String owner, String name, String descriptor){
        visitor.visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, descriptor, false);
    }

    public final void invokevirtual(String owner, String name, String descriptor){
        visitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, descriptor, false);
    }

    public final void invokestatic(Method method){
        visitor.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            Type.getInternalName(method.getDeclaringClass()),
            method.getName(),
            Type.getMethodDescriptor(method),
            false
        );
    }

    public final void newarray(Class<?> type){
        if(type == boolean.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BOOLEAN);
        }
        else if(type == char.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_CHAR);
        }
        else if(type == float.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_FLOAT);
        }
        else if(type == double.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_DOUBLE);
        }
        else if(type == byte.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BYTE);
        }
        else if(type == short.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_SHORT);
        }
        else if(type == int.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT);
        }
        else if(type == long.class){
            visitor.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_LONG);
        }
        else{
            visitor.visitTypeInsn(Opcodes.ANEWARRAY, Type.getInternalName(type));
        }
    }

    public final void tastore(Class<?> elementType){
        if(elementType == boolean.class || elementType == byte.class){
            bastore();
        }
        else if(elementType == char.class){
            castore();
        }
        else if(elementType == short.class){
            sastore();
        }
        else if(elementType == int.class){
            iastore();
        }
        else if(elementType == long.class){
            lastore();
        }
        else if(elementType == float.class){
            fastore();
        }
        else if(elementType == double.class){
            dastore();
        }
        else{
            aastore();
        }
    }

    public final void bastore(){
        visitor.visitInsn(Opcodes.BASTORE);
    }

    public final void castore(){
        visitor.visitInsn(Opcodes.CASTORE);
    }

    public final void sastore(){
        visitor.visitInsn(Opcodes.SASTORE);
    }

    public final void iastore(){
        visitor.visitInsn(Opcodes.IASTORE);
    }

    public final void lastore(){
        visitor.visitInsn(Opcodes.LASTORE);
    }

    public final void fastore(){
        visitor.visitInsn(Opcodes.FASTORE);
    }

    public final void dastore(){
        visitor.visitInsn(Opcodes.DASTORE);
    }

    public final void aastore(){
        visitor.visitInsn(Opcodes.AASTORE);
    }

    public final void aaload(){
        visitor.visitInsn(Opcodes.AALOAD);
    }

    public final void iaload(){
        visitor.visitInsn(Opcodes.IALOAD);
    }

    public final void dup(){
        visitor.visitInsn(Opcodes.DUP);
    }

    public final void label(Label label){
        visitor.visitLabel(label);
    }

    public final void exception(Class<?> type, Label start, Label end, Label handler){
        visitor.visitTryCatchBlock(start, end, handler, type != null ? Type.getInternalName(type) : null);
    }

    public final void athrow(){
        visitor.visitInsn(Opcodes.ATHROW);
    }

    public final void jmp(Label label){
        visitor.visitJumpInsn(Opcodes.GOTO, label);
    }

    public final void pop(){
        visitor.visitInsn(Opcodes.POP);
    }

}
