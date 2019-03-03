package org.c41.expression4j;

final class ClassStringBuilder {

    private final StringBuilder sb;

    ClassStringBuilder(){
        this.sb = new StringBuilder();
    }

    public ClassStringBuilder appendLine(String value){
        sb.append(value);
        return this;
    }

}
