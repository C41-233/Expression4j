package org.c41.expression4j;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

final class BodyEmit extends MethodEmit {

    private final Expression body;
    private final ParameterExpression[] parameters;
    private final Method method;

    protected BodyEmit(ClassVisitor writer, Method method, Expression body, ParameterExpression[] parameters) {
        super(writer.visitMethod(
            Opcodes.ACC_PUBLIC | Opcodes.ACC_FINAL,
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
            ParameterStack.declareParameter(parameter);
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
        load(ParameterStack.getParameterSlot(parameter), parameter.getExpressionType());
    }

    public final void store(ParameterExpression parameter) {
        store(ParameterStack.getParameterSlot(parameter), parameter.getExpressionType());
    }

    public final ParameterStack ParameterStack = new ParameterStack();

    public final RedirectControlFlow RedirectReturnControlFlow = new RedirectControlFlow();

    public final JmpTargetControlFlow JmpTargetControlFlow = new JmpTargetControlFlow();

    public final Label declareLabel(){
        Label label = new Label();
        RedirectReturnControlFlow.declareLabel(label);
        return label;
    }

    @Override
    public String toString() {
        ClassStringBuilder sb = new ClassStringBuilder();
        sb.append("public ")
                .append(method.getReturnType().getSimpleName())
                .append(' ')
                .append(method.getName())
        ;
        sb.append('(');
        for (int i = 0; i < parameters.length; i++) {
            ParameterExpression expression = parameters[i];
            sb.append(expression.getExpressionType().getSimpleName()).append(' ');
            expression.toString(sb, 0);
            if(i != parameters.length - 1){
                sb.append(", ");
            }
        }
        sb.append(')');
        sb.appendLine("{");

        Class<?> stackType = body.getExpressionType();

        sb.pushIndent();
        {
            if(stackType == null){
                body.toString(sb, CodeStyle.AlreadyBlock);
            }
            else{
                sb.append("return ");
                body.toString(sb, CodeStyle.AlreadyBlock | CodeStyle.Statement);
            }
        }
        sb.popIndent();
        sb.appendLine();
        sb.append("}");

        return sb.toString();
    }
}
