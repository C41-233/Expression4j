package testcase;

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
    public void tryCatchFinallyReturnInTry1(){
        Func<String> r = Expressions.compile(Func.class,
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
    public void tryCatchFinallyReturnInTry2(){
        Func<String> r = Expressions.compile(Func.class,
            Expressions.tryCatchFinally(
                Expressions.ret(Expressions.constant("try")),
                Expressions.empty()
            )
        );
        Assert.assertEquals("try", r.invoke());
    }

    @Test
    public void tryCatchFinallyReturnInTry3(){
        Func<String> r = Expressions.compile(Func.class,
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
    public void tryCatchFinallyReturnInTry4() throws NoSuchMethodException {
        Func<String> r = Expressions.compile(Func.class,
            Expressions.block(
                Expressions.tryCatchFinally(
                    Expressions.ret(Expressions.constant("try")),
                    Expressions.call(TestExceptionHandler.class.getMethod("runFinally"))
                ),
                Expressions.ret(Expressions.constant("after"))
            )
        );
        Assert.assertEquals("try", r.invoke());
        Assert.assertEquals("finally", sb.toString());
    }

    /*  String func():
            try{
                runTryThrow();
                return "try";
            }
            catch(IOException e){
                runCatch();
                return "catch";
            }
            finally{
            }
            return "after";
     */
    @Test
    public void tryCatchFinallyReturnInCatch1() throws NoSuchMethodException {
        Func<String> r = Expressions.compile(Func.class,
            Expressions.block(
                Expressions.tryCatchFinally(
                    Expressions.block(
                        Expressions.call(TestExceptionHandler.class.getMethod("runTryThrow")),
                        Expressions.ret(Expressions.constant("try"))
                    ),
                    Expressions.empty(),
                    Expressions.catchBlock(
                        IOException.class,
                        Expressions.block(
                            Expressions.call(TestExceptionHandler.class.getMethod("runCatch")),
                            Expressions.ret(Expressions.constant("catch"))
                        )
                    )
                ),
                Expressions.ret(Expressions.constant("after"))
            )
        );
        Assert.assertEquals("catch", r.invoke());
        Assert.assertEquals("trycatch", sb.toString());
    }

    /*
        String func():
            try{
                runTryThrow();
                return "try";
            }
            catch(IOException e){
                runCatch();
                return "catch";
            }
            finally{
                runFinally();
                return "finally";
            }
            return "after";
     */
    @Test
    public void tryCatchFinallyReturnInCatch2() throws NoSuchMethodException {
        Func<String> r = Expressions.compile(Func.class,
            Expressions.block(
                Expressions.tryCatchFinally(
                    Expressions.block(
                        Expressions.call(TestExceptionHandler.class.getMethod("runTryThrow")),
                        Expressions.ret(Expressions.constant("try"))
                    ),
                    Expressions.block(
                        Expressions.call(TestExceptionHandler.class.getMethod("runFinally")),
                        Expressions.ret(Expressions.constant("finally"))
                    ),
                    Expressions.catchBlock(
                        IOException.class,
                        Expressions.block(
                            Expressions.call(TestExceptionHandler.class.getMethod("runCatch")),
                            Expressions.ret(Expressions.constant("catch"))
                        )
                    )
                ),
                Expressions.ret(Expressions.constant("after"))
            )
        );
        Assert.assertEquals("finally", r.invoke());
        Assert.assertEquals("trycatchfinally", sb.toString());
    }

    /*
        void func(StringBuilder sb):
            try{
                sb.append(try1);
                try{
                    sb.append(try2);
                    throw new IOException();
                }
                catch(IOException e){
                    sb.append("catch2");
                    throw e;
                }
            }
            catch(IOException e){
                sb.append("catch1");
            }
            finally{
                sb.append("finally");
            }
            sb.append("after");
     */
    @Test
    public void tryCatchFinallyReturnInCatch3() throws NoSuchMethodException {
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
