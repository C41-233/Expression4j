package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

public class TestStaticCall {

    @Test
    public void sin() throws NoSuchMethodException {
        ParameterExpression x = Expressions.parameter(double.class);
        FuncDD r = Expressions.compile(FuncDD.class,
                Expressions.call(
                        Math.class.getMethod("sin", double.class),
                        x
                ),
                x);
        Assert.assertEquals(Math.sin(40), r.invoke(40), 0);
    }

    @Test
    public void format() throws NoSuchMethodException {
        ParameterExpression x = Expressions.parameter(String.class);
        ParameterExpression y = Expressions.parameter(Object[].class);
        FuncStringObjectsString r = Expressions.compile(FuncStringObjectsString.class,
            Expressions.call(
                String.class.getMethod("format", String.class, Object[].class),
                x,
                y
            ),
        x, y);
        Assert.assertEquals(String.format("%d%s",1, "ss"), r.invoke("%d%s", 1, "ss"));
    }

    @Test
    public void format2() throws NoSuchMethodException {
        ParameterExpression x = Expressions.parameter(String.class);
        ParameterExpression y = Expressions.parameter(Object[].class);
        FuncStringObjectsString r = Expressions.compile(FuncStringObjectsString.class,
                Expressions.call(
                    String.class.getMethod("format", String.class, Object[].class),
                    x, y
                ),
                x, y);
        final String msg = "%d%f%s%c";
        Assert.assertEquals(
            String.format(msg, 1, 1.2, "value", 'x'),
            r.invoke(msg, 1, 1.2, "value", 'x')
        );
        System.out.println(r.invoke(msg, 1, 1.2, "value", 'x'));
    }

}
