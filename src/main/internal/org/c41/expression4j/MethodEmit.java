package org.c41.expression4j;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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

    public final void tload(int n, Class<?> type){
        switch (TypeUtils.getStackType(type)){
            case Int:
                iload(n);
                break;
            case Long:
                lload(n);
                break;
            case Float:
                break;
            case Double:
                break;
            case Reference:
                aload(n);
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
                throw CompileExpression.badOperator();
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
                throw CompileExpression.badOperator();
        }
    }

    public final void ret(){
        visitor.visitInsn(Opcodes.RETURN);
    }

    public final void iret() {
        visitor.visitInsn(Opcodes.IRETURN);
    }

    public final void lret(){
        visitor.visitInsn(Opcodes.LRETURN);
    }

    final void tret(Class<?> type){
        if(type == null || type == void.class){
            ret();
            return;
        }
        switch (TypeUtils.getStackType(type)){
            case Int:
                iret();
                break;
            case Long:
                lret();
                break;
            case Float:
                break;
            case Double:
                break;
            case Reference:
                break;
        }
    }

    public final void getfield(String owner, String name, String descriptor){
        visitor.visitFieldInsn(Opcodes.GETFIELD,owner, name, descriptor);
    }

    public final void putField(String owner, String name, String descriptor){
       visitor.visitFieldInsn(Opcodes.PUTFIELD, owner, name, descriptor);
    }

    public final void i2l(){
        visitor.visitInsn(Opcodes.I2L);
    }

    public final void l2i(){
        visitor.visitInsn(Opcodes.L2I);
    }

    public final void invokespecial(String owner, String name, String descriptor){
        visitor.visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, descriptor, false);
    }

}
