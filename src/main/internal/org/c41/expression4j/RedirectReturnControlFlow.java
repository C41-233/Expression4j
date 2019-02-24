package org.c41.expression4j;

import org.objectweb.asm.Label;

import java.util.ArrayList;

final class RedirectReturnControlFlow {

    private static class RedirectFrame{
        public final Label label;
        public boolean trigger;

        public RedirectFrame(Label label){
            this.label = label;
        }
    }

    private ArrayList<RedirectFrame> redirectControlFlows = new ArrayList<>();

    public final void pushRedirect(Label label){
        redirectControlFlows.add(new RedirectFrame(label));
    }

    public final void popRedirect(){
        redirectControlFlows.remove(redirectControlFlows.size() - 1);
    }

    public final Label getCurrentRedirectTarget(){
        if(redirectControlFlows.size() == 0){
            return null;
        }
        return redirectControlFlows.get(redirectControlFlows.size() - 1).label;
    }

    public final void trigger(){
        redirectControlFlows.get(redirectControlFlows.size() - 1).trigger = true;
    }

    public final boolean isTriggered(){
        return redirectControlFlows.get(redirectControlFlows.size() - 1).trigger;
    }

}
