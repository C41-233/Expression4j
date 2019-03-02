package org.c41.expression4j;

import org.objectweb.asm.Label;

import java.util.ArrayList;

final class JmpTargetControlFlow {

    private static class JmpTarget {
        public final Label BreakTarget;
        public final Label ContinueTarget;

        public JmpTarget(Label breakTarget, Label continueTarget){
            this.BreakTarget = breakTarget;
            this.ContinueTarget = continueTarget;
        }
    }

    private final ArrayList<JmpTarget> list = new ArrayList<>();

    public final void pushJmpTarget(Label breakTarget, Label continueTarget){
        list.add(new JmpTarget(breakTarget, continueTarget));
    }

    public final Label getBreakTarget(){
        if(list.size() > 0){
            return list.get(list.size() - 1).BreakTarget;
        }
        return null;
    }

    public final Label getContinueTarget(){
        if(list.size() > 0){
            return list.get(list.size() - 1).ContinueTarget;
        }
        return null;
    }

    public final void popJmpTarget(){
        list.remove(list.size() - 1);
    }

}
