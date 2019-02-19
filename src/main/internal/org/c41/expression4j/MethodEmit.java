package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
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

    final void aload(int n){
        visitor.visitVarInsn(Opcodes.ALOAD, n);
    }

    final void iload(int n){
        visitor.visitVarInsn(Opcodes.ILOAD, n);
    }

    final void tload(int n, Class<?> type){
        if(TypeUtils.isIntType(type)){
            iload(n);
        }
        else{
            aload(n);
        }
    }

    final void iadd(){
        visitor.visitInsn(Opcodes.IADD);
    }

    final void isub(){
        visitor.visitInsn(Opcodes.ISUB);
    }

    final void ret(){
        visitor.visitInsn(Opcodes.RETURN);
    }

    final void iret() {
        visitor.visitInsn(Opcodes.IRETURN);
    }

    final void tret(Class<?> type){
        if(TypeUtils.isIntType(type)){
            iret();
            return;
        }
        ret();
    }

    final void getfield(String owner, String name, String descriptor){
        visitor.visitFieldInsn(Opcodes.GETFIELD,owner, name, descriptor);
    }

    final void invokespecial(String owner, String name, String descriptor){
        visitor.visitMethodInsn(Opcodes.INVOKESPECIAL, owner, name, descriptor, false);
    }
}
