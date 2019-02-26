package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

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
        Func<String> r = Expressions.Compile(Func.class,
            Expressions.TryCatchFinally(
                Expressions.Return(Expressions.Constant("try")),
                Expressions.Return(Expressions.Constant("finally")),
                Expressions.CatchBlock(
                    Exception.class,
                    Expressions.Return(Expressions.Constant("catch"))
                )
            )
        );
       Assert.assertEquals("finally", r.invoke());
    }

    @Test
    public void tryCatchFinallyReturnInTry2(){
        Func<String> r = Expressions.Compile(Func.class,
            Expressions.TryCatchFinally(
                Expressions.Return(Expressions.Constant("try")),
                Expressions.Empty()
            )
        );
        Assert.assertEquals("try", r.invoke());
    }

    @Test
    public void tryCatchFinallyReturnInTry3(){
        Func<String> r = Expressions.Compile(Func.class,
            Expressions.Block(
                Expressions.TryCatchFinally(
                    Expressions.Return(Expressions.Constant("try")),
                    Expressions.Empty()
                ),
                Expressions.Return(Expressions.Constant("after"))
            )
        );
        Assert.assertEquals("try", r.invoke());
    }

    @Test
    public void tryCatchFinallyReturnInTry4() throws NoSuchMethodException {
        Func<String> r = Expressions.Compile(Func.class,
            Expressions.Block(
                Expressions.TryCatchFinally(
                    Expressions.Return(Expressions.Constant("try")),
                    Expressions.Call(TestExceptionHandler.class.getMethod("runFinally"))
                ),
                Expressions.Return(Expressions.Constant("after"))
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
        Func<String> r = Expressions.Compile(Func.class,
            Expressions.Block(
                Expressions.TryCatchFinally(
                    Expressions.Block(
                        Expressions.Call(TestExceptionHandler.class.getMethod("runTryThrow")),
                        Expressions.Return(Expressions.Constant("try"))
                    ),
                    Expressions.Empty(),
                    Expressions.CatchBlock(
                        IOException.class,
                        Expressions.Block(
                            Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")),
                            Expressions.Return(Expressions.Constant("catch"))
                        )
                    )
                ),
                Expressions.Return(Expressions.Constant("after"))
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
        Func<String> r = Expressions.Compile(Func.class,
            Expressions.Block(
                Expressions.TryCatchFinally(
                    Expressions.Block(
                        Expressions.Call(TestExceptionHandler.class.getMethod("runTryThrow")),
                        Expressions.Return(Expressions.Constant("try"))
                    ),
                    Expressions.Block(
                        Expressions.Call(TestExceptionHandler.class.getMethod("runFinally")),
                        Expressions.Return(Expressions.Constant("finally"))
                    ),
                    Expressions.CatchBlock(
                        IOException.class,
                        Expressions.Block(
                            Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")),
                            Expressions.Return(Expressions.Constant("catch"))
                        )
                    )
                ),
                Expressions.Return(Expressions.Constant("after"))
            )
        );
        Assert.assertEquals("finally", r.invoke());
        Assert.assertEquals("trycatchfinally", sb.toString());
    }

    /*
        void func(StringBuilder sb):
            sb.append("before");
            try{
                sb.append("try1");
            }
            catch(RuntimeException e){
                sb.append("catch1");
            }
            finally{
                sb.append("finally");
            }
            sb.append("after");
     */
    @Test
    public void tryCatchFinallyReturnInCatch3() throws NoSuchMethodException {
        Method method = StringBuilder.class.getMethod("append", String.class);
        ParameterExpression p = Expressions.Parameter(StringBuilder.class);
        Action1<StringBuilder> r = Expressions.Compile(Action1.class,
            Expressions.Block(
                Expressions.Call(p, method, Expressions.Constant("before")),
                Expressions.TryCatchFinally(
                    Expressions.Block(
                        Expressions.Call(p, method, Expressions.Constant("try1"))
                    ),
                    Expressions.Block(
                        Expressions.Call(p, method, Expressions.Constant("finally"))
                    ),
                    Expressions.CatchBlock(
                        RuntimeException.class,
                        Expressions.Call(p, method, Expressions.Constant("catch1"))
                    )
                ),
                Expressions.Call(p, method, Expressions.Constant("after"))
            ),
            p
        );
        StringBuilder sb = new StringBuilder();
        r.invoke(sb);
        Assert.assertEquals("beforetry1finallyafter", sb.toString());
    }

    /*
        void func(StringBuilder sb):
            sb.append("before");
            try{
                sb.append("try1");
                try{
                    sb.append("try2");
                }
                finally{
                    sb.append(finally2);
                }
            }
            catch(RuntimeException e){
                sb.append("catch1");
            }
            finally{
                sb.append("finally1");
            }
            sb.append("after");
     */
    @Test
    public void tryCatchFinallyReturnInCatch4() throws NoSuchMethodException {
        Method method = StringBuilder.class.getMethod("append", String.class);
        ParameterExpression p = Expressions.Parameter(StringBuilder.class);
        Action1<StringBuilder> r = Expressions.Compile(Action1.class,
            Expressions.Block(
                Expressions.Call(p, method, Expressions.Constant("before")),
                Expressions.TryCatchFinally(
                    Expressions.Block(
                        Expressions.Call(p, method, Expressions.Constant("try1")),
                        Expressions.TryFinally(
                            Expressions.Call(p, method, Expressions.Constant("try2")),
                            Expressions.Call(p, method, Expressions.Constant("finally2"))
                        )
                    ),
                    Expressions.Block(
                        Expressions.Call(p, method, Expressions.Constant("finally1"))
                    ),
                    Expressions.CatchBlock(
                        RuntimeException.class,
                        Expressions.Call(p, method, Expressions.Constant("catch1"))
                    )
                ),
                Expressions.Call(p, method, Expressions.Constant("after"))
            ),
            p
        );
        StringBuilder sb = new StringBuilder();
        r.invoke(sb);
        Assert.assertEquals("beforetry1try2finally2finally1after", sb.toString());
    }

    @Test
    public void tryCatchFinally1() throws NoSuchMethodException {
        Action r = Expressions.Compile(Action.class,
            Expressions.TryCatchFinally(
                Expressions.Call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.Call(TestExceptionHandler.class.getMethod("runFinally")),
                Expressions.CatchBlock(Exception.class, Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("tryfinally", sb.toString());
    }

    @Test
    public void tryCatchFinally2() throws NoSuchMethodException {
        Action r = Expressions.Compile(Action.class,
            Expressions.TryCatchFinally(
                Expressions.Call(TestExceptionHandler.class.getMethod("runTryThrow")),
                Expressions.Call(TestExceptionHandler.class.getMethod("runFinally")),
                Expressions.CatchBlock(Exception.class, Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("trycatchfinally", sb.toString());
    }

    @Test
    public void tryFinally() throws NoSuchMethodException {
        Action r = Expressions.Compile(Action.class,
            Expressions.TryCatchFinally(
                Expressions.Call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.Call(TestExceptionHandler.class.getMethod("runFinally"))
            )
        );
        r.invoke();
        Assert.assertEquals("tryfinally", sb.toString());
    }

    @Test
    public void tryCatch1() throws NoSuchMethodException {
        Action r = Expressions.Compile(Action.class,
            Expressions.TryCatch(
                Expressions.Call(TestExceptionHandler.class.getMethod("runTry")),
                Expressions.CatchBlock(Exception.class, Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")))
            )
        );
        r.invoke();
        Assert.assertEquals("try", sb.toString());
    }

    @Test
    public void tryCatch2() throws NoSuchMethodException {
        Action r = Expressions.Compile(Action.class,
                Expressions.TryCatch(
                        Expressions.Call(TestExceptionHandler.class.getMethod("runTryThrow")),
                        Expressions.CatchBlock(RuntimeException.class, Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")))
                )
        );
        Assert.assertThrows(IOException.class, () -> r.invoke());
        Assert.assertEquals("try", sb.toString());
    }

    @Test
    public void tryCatch3() throws NoSuchMethodException {
        Action r = Expressions.Compile(Action.class,
            Expressions.TryCatch(
                Expressions.Call(TestExceptionHandler.class.getMethod("runTryThrow")),
                Expressions.CatchBlock(IOException.class, Expressions.Call(TestExceptionHandler.class.getMethod("runCatch")))
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
