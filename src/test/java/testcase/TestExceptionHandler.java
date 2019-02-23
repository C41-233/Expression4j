package testcase;

import org.c41.expression4j.Expression;
import org.c41.expression4j.Expressions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestExceptionHandler {

    private static StringBuilder sb;

    @Before
    public void before(){
        sb = new StringBuilder();
    }

    @After
    public void after(){
        sb = null;
    }

    @Test
    public void tryCatchFinallyReturn1(){
        FuncString r = Expressions.compile(FuncString.class,
            Expressions.tryCatchFinally(
                Expressions.ret(Expressions.constant("try")),
                Expressions.ret(Expressions.constant("finally")),
                Expressions.catchBlock(
                    Exception.class,
                    Expressions.ret(Expressions.constant("catch"))
                )
            )
        );
       Assert.assertEquals("finally", r.invoke());
    }

    @Test
    public void tryCatchFinallyReturn2(){
        FuncString r = Expressions.compile(FuncString.class,
            Expressions.tryCatchFinally(
                Expressions.ret(Expressions.constant("try")),
                Expressions.empty()
            )
        );
        Assert.assertEquals("try", r.invoke());
    }

    @Test
    public void tryCatchFinallyReturn3(){
        FuncString r = Expressions.compile(FuncString.class,
            Expressions.block(
                Expressions.tryCatchFinally(
                    Expressions.ret(Expressions.constant("try")),
                    Expressions.empty()
                ),
                Expressions.ret(Expressions.constant("after"))
            )
        );
        Assert.assertEquals("try", r.invoke());
    }

    @Test
    public void tryCatchFinally1() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
            Expressions.tryCatchFinally(
                Expressions.call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.call(TestExceptionHandler.class.getMethod("runFinally")),
                Expressions.catchBlock(Exception.class, Expressions.call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("tryfinally", sb.toString());
    }

    @Test
    public void tryCatchFinally2() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
            Expressions.tryCatchFinally(
                Expressions.call(TestExceptionHandler.class.getMethod("runTryThrow")),
                Expressions.call(TestExceptionHandler.class.getMethod("runFinally")),
                Expressions.catchBlock(Exception.class, Expressions.call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("trycatchfinally", sb.toString());
    }

    @Test
    public void tryFinally() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
            Expressions.tryCatchFinally(
                Expressions.call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.call(TestExceptionHandler.class.getMethod("runFinally"))
            )
        );
        r.invoke();
        Assert.assertEquals("tryfinally", sb.toString());
    }

    @Test
    public void tryCatch1() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
            Expressions.tryCatch(
                Expressions.call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.catchBlock(Exception.class, Expressions.call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("try", sb.toString());
    }

    @Test
    public void tryCatch2() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
                Expressions.tryCatch(
                        Expressions.call(TestExceptionHandler.class.getMethod("runTryThrow")),
                        Expressions.catchBlock(RuntimeException.class, Expressions.call(TestExceptionHandler.class.getMethod("runCatch")))
                )
        );
        Assert.assertThrows(IOException.class, () -> r.invoke());
        Assert.assertEquals("try", sb.toString());
    }

    @Test
    public void tryCatch3() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
            Expressions.tryCatch(
                Expressions.call(TestExceptionHandler.class.getMethod("runTryThrow")),
                Expressions.catchBlock(IOException.class, Expressions.call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("trycatch", sb.toString());
    }

    public static void runTry() throws Exception{
        sb.append("try");
    }

    public static void runTryThrow() throws Exception{
        sb.append("try");
        throw new IOException();
    }

    public static void runCatch(){
        sb.append("catch");
    }

    public static void runFinally(){
        sb.append("finally");
    }

}
