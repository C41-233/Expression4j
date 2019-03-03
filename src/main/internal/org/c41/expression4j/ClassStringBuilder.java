package org.c41.expression4j;

final class CodeStyle {
    public static final int None = 0;
    public static final int Advance = 0x01;
    public static final int AlreadyBlock = 0x02;
    public static final int Declare = 0x04;
    public static final int Statement = 0x08;

    public static boolean is(int value, int mask){
        return (value & mask) != 0;
    }
}

final class ClassStringBuilder {

    private final StringBuilder sb;
    private int indent = 0;

    private boolean newLine = true;

    ClassStringBuilder(){
        this.sb = new StringBuilder();
    }

    public ClassStringBuilder appendLine(String value){
        indent();
        sb.append(value);
        return appendLine();
    }

    public ClassStringBuilder appendLine(String format, Object... args){
        return appendLine(String.format(format, args));
    }

    public ClassStringBuilder appendLine(char value){
        indent();
        sb.append(value);
        return appendLine();
    }

    public ClassStringBuilder appendLine(){
        indent();
        sb.append('\n');
        newLine = true;
        return this;
    }

    public ClassStringBuilder append(String value){
        indent();
        sb.append(value);
        return this;
    }

    public ClassStringBuilder append(char value){
        indent();
        sb.append(value);
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    public void pushIndent() {
        indent++;
    }

    public void popIndent(){
        indent--;
    }

    private void indent(){
        if(newLine){
            for(int i=0; i<indent; i++){
                sb.append('\t');
            }
            newLine = false;
        }
    }

}
