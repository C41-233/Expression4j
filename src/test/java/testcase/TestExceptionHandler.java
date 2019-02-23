package testcase;

import org.c41.expression4j.Expression;
import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void tryCatchFinally1() throws NoSuchFieldException {
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
       Assert.assertEquals("try", r.invoke());
    }

    @Test
    public void tryCatchFinally2() throws NoSuchMethodException {
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
    public void tryCatchFinally3() throws NoSuchMethodException {
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
    public void tryCatch() throws NoSuchMethodException {
        Action r = Expressions.compile(Action.class,
            Expressions.tryCatch(
                Expressions.call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.catchBlock(Exception.class, Expressions.call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("try", sb.toString());
    }

    public static void runTry() throws Exception{
        sb.append("try");
    }

    public static void runTryThrow() throws Exception{
        sb.append("try");
        throw new Exception();
    }

    public static void runCatch(){
        sb.append("catch");
    }

    public static void runFinally(){
        sb.append("finally");
    }

}
