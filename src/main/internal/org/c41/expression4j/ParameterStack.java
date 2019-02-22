package org.c41.expression4j;

import java.util.ArrayList;

final class ParameterStack {

    private ArrayList<ParameterSlot> parameters = new ArrayList<>();
    private int currentStack = 0;
    private int nextSlot = 1;

    private static class ParameterSlot{
        public final ParameterExpression parameter;
        public final int slot;
        public final int stack;

        public ParameterSlot(ParameterExpression parameter, int slot, int stack){
            this.parameter = parameter;
            this.slot = slot;
            this.stack = stack;
        }
    }

    public final void declareParameter(ParameterExpression parameter){
        if(getParameterSlotInternal(parameter) != null){
            return;
        }
        parameters.add(new ParameterSlot(parameter, nextSlot, currentStack));
        nextSlot += TypeUtils.getSlotCount(parameter.getExpressionType());
    }

    public final void pushScope(){
        currentStack++;
    }

    public final void popScope(){
        for(int i = parameters.size() -1; i>= 0; --i){
            ParameterSlot slot = parameters.get(i);
            if(slot.stack >= currentStack){
                parameters.remove(i);
                nextSlot -= TypeUtils.getSlotCount(slot.parameter.getExpressionType());
            }
            else{
                break;
            }
        }
        currentStack--;
    }

    private final Integer getParameterSlotInternal(ParameterExpression parameter){
        for(int i = parameters.size() -1; i>= 0; --i){
            ParameterSlot slot = parameters.get(i);
            if(slot.parameter == parameter){
                return slot.slot;
            }
        }
        return null;
    }

    public final int getParameterSlot(ParameterExpression parameter){
        Integer slot = getParameterSlotInternal(parameter);
        if(slot != null){
            return slot;
        }
        throw CompileException.parameterNotDeclare(parameter);
    }

}
