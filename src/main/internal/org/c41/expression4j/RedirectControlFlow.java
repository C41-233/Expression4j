package org.c41.expression4j;

import org.objectweb.asm.Label;

import java.util.ArrayList;

final class RedirectControlFlow {

    private static class RedirectFrame{
        public final Label label;
        public boolean trigger;

        public RedirectFrame(Label label){
            this.label = label;
        }
    }

    private ArrayList<RedirectFrame> redirectControlFlows = new ArrayList<>();

    public final void pushRedirectControlFlow(Label label){
        redirectControlFlows.add(new RedirectFrame(label));
    }

    public final void popRedirectControlFlow(){
        redirectControlFlows.remove(redirectControlFlows.size() - 1);
    }

    public final Label getRedirectControlFlow(){
        if(redirectControlFlows.size() == 0){
            return null;
        }
        return redirectControlFlows.get(redirectControlFlows.size() - 1).label;
    }

    public final void onReturn(){
        redirectControlFlows.get(redirectControlFlows.size() - 1).trigger = true;
    }

    public final boolean isTrigger(){
        return redirectControlFlows.get(redirectControlFlows.size() - 1).trigger;
    }

}
