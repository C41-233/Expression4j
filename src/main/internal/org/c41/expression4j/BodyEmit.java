package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

final class BodyEmit extends MethodEmit {

    private final Expression body;
    private final ParameterExpression[] parameters;
    private final Class<?> returnType;

    protected BodyEmit(ClassVisitor writer, Method method, Expression body, ParameterExpression[] parameters) {
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
            declareParameter(parameters[i]);
        }
        body.emit(this);
        treturn(returnType);
    }

    public final void tload(ParameterExpression parameter){
        tload(getParameterSlot(parameter), parameter.getExpressionType());
    }

    public void tstore(ParameterExpression parameter) {
        tstore(getParameterSlot(parameter), parameter.getExpressionType());
    }

    private final ParameterStack parameterStack = new ParameterStack();

    public final void declareParameter(ParameterExpression parameter){
        parameterStack.declareParameter(parameter);
    }

    public final void pushScope(){
        parameterStack.pushScope();
    }

    public final void popScope(){
        parameterStack.popScope();
    }

    public final int getParameterSlot(ParameterExpression parameter){
        return parameterStack.getParameterSlot(parameter);
    }

}
