package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

final class BodyEmit extends MethodEmit {

    private final Expression body;
    private final ParameterExpression[] parameters;
    private final Method method;

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
        this.method = method;
    }

    public Class<?> getReturnType(){
        return method.getReturnType();
    }

    @Override
    protected void emitCodes() {
        Class<?>[] inputTypes = method.getParameterTypes();
        for(int i=0; i<parameters.length; i++){
            ParameterExpression parameter = parameters[i];
            Class<?> inputType = inputTypes[i];
            Class<?> targetType = parameter.getExpressionType();
            declareParameter(parameter);
            if(inputType != targetType){
                load(parameter);
                checkcast(targetType);
                store(parameter);
            }
        }

        Class<?> stackType = body.getExpressionType();
        if(stackType == null){
            body.emitBalance(this);
            ret();
        }
        else{
            body.emitRead(this);
            ret(getReturnType());
        }
    }

    public final void load(ParameterExpression parameter){
        load(getParameterSlot(parameter), parameter.getExpressionType());
    }

    public final void store(ParameterExpression parameter) {
        store(getParameterSlot(parameter), parameter.getExpressionType());
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

    public final RedirectReturnControlFlow RedirectReturnControlFlow = new RedirectReturnControlFlow();

}
