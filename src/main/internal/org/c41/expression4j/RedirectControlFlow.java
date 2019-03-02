package org.c41.expression4j;

import org.objectweb.asm.Label;

import java.util.ArrayList;
import java.util.List;

final class RedirectFrame{
    public final Label JmpLabel;
    public final Label RedirectLabel;

    RedirectFrame(Label jmpLabel, Label redirectLabel){
        this.JmpLabel = jmpLabel;
        this.RedirectLabel = redirectLabel;
    }

    RedirectFrame(Label jmpLabel){
        this(jmpLabel, null);
    }

}

final class RedirectControlFlow {

    private final ArrayList<RedirectStack> redirectControlFlows = new ArrayList<>();

    public final void pushRedirect() {
        redirectControlFlows.add(new RedirectStack());
    }

    public final List<RedirectFrame> popRedirect() {
        List<RedirectFrame> frames = currentStack().getAllRedirects();
        redirectControlFlows.remove(redirectControlFlows.size() - 1);
        return frames;
    }

    private RedirectStack currentStack() {
        if (redirectControlFlows.size() == 0) {
            return null;
        }
        return redirectControlFlows.get(redirectControlFlows.size() - 1);
    }

    public final void declareLabel(Label label) {
        RedirectStack stack = currentStack();
        if (stack == null) {
            return;
        }
        stack.declareLabel(label);
    }

    public final Label redirectReturn(){
        RedirectStack stack = currentStack();
        if(stack == null){
            return null;
        }
        return stack.redirectReturn();
    }

    public final Label redirectLabel(Label label){
        RedirectStack stack = currentStack();
        //current no redirect stack then just goto label
        if(stack == null){
            return null;
        }
        return stack.redirectLabel(label);
    }

}

final class RedirectStack {

    private final ArrayList<RedirectFrame> frames = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();

    public void addFrame(Label redirectLabel){
        Label jmpLabel = new Label();
    }

    public void declareLabel(Label label){
        if (labels.contains(label)) {
            throw Error.badOperator();
        }
        labels.add(label);
    }

    public Label redirectLabel(Label label){
        //this label is in crrent redirect stack, then just goto label
        if(labels.contains(label)){
            return null;
        }

        for(RedirectFrame frame : frames){
            if(frame.RedirectLabel == label){
                return frame.JmpLabel;
            }
        }

        Label jmpLabel = new Label();
        frames.add(new RedirectFrame(jmpLabel, label));
        return jmpLabel;
    }

    public Label redirectReturn(){
        for(RedirectFrame frame : frames){
            if(frame.RedirectLabel == null){
                return frame.JmpLabel;
            }
        }
        Label jmpLabel = new Label();
        frames.add(new RedirectFrame(jmpLabel));
        return jmpLabel;
    }

    public List<RedirectFrame> getAllRedirects() {
        return frames;
    }
}
