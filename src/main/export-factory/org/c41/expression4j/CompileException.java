package org.c41.expression4j;

public class CompileException extends RuntimeException{

    public CompileException(String msg) {
        super(msg);
    }

    public CompileException(String msg, Throwable e) {
        super(msg, e);
    }

}
