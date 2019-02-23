package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class TestStaticCall {

    @Test
    public void sin() throws NoSuchMethodException {
        ParameterExpression x = Expressions.Parameter(double.class);
        FuncDD r = Expressions.Compile(FuncDD.class,
                Expressions.Call(
                        Math.class.getMethod("sin", double.class),
                        x
                ),
                x);
        Assert.assertEquals(Math.sin(40), r.invoke(40), 0);
    }

    @Test
    public void format1() throws NoSuchMethodException {
        ParameterExpression x = Expressions.Parameter(String.class);
        ParameterExpression y = Expressions.Parameter(Object[].class);
        FuncVar2<String, Object, String> r = Expressions.Compile(FuncVar2.class,
            Expressions.Call(
                String.class.getMethod("format", String.class, Object[].class),
                x,
                y
            ),
        x, y);
        Assert.assertEquals(String.format("%d%s",1, "ss"), r.invoke("%d%s", 1, "ss"));
    }

    @Test
    public void format2() throws NoSuchMethodException {
        ParameterExpression x = Expressions.Parameter(String.class);
        ParameterExpression y = Expressions.Parameter(Object[].class);
        FuncVar2<String, Object, String> r = Expressions.Compile(FuncVar2.class,
                Expressions.Call(
                    String.class.getMethod("format", String.class, Object[].class),
                    x, y
                ),
                x, y);
        final String msg = "%d%f%s%c";
        Assert.assertEquals(
            String.format(msg, 1, 1.2, "value", 'x'),
            r.invoke(msg, 1, 1.2, "value", 'x')
        );
    }

    /*
        func(StringBuilder sb)
            sb.append("x")
            sb.append("y")
            sb.append("z")
     */
    @Test
    public void append() throws NoSuchMethodException {
        ParameterExpression sb = Expressions.Parameter(StringBuilder.class);
        Method method = StringBuilder.class.getMethod("append", String.class);
        Action1<StringBuilder> r = Expressions.Compile(Action1.class,
            Expressions.Block(
                Expressions.Call(sb, method, Expressions.Constant("x")),
                Expressions.Call(sb, method, Expressions.Constant("y")),
                Expressions.Call(sb, method, Expressions.Constant("z"))
            ),
            sb
        );
        StringBuilder sb0 = new StringBuilder();
        r.invoke(sb0);
        Assert.assertEquals("xyz", sb0.toString());
    }

}
