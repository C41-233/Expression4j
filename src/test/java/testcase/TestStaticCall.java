package testcase;

import org.c41.expression4j.Expression;
import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

public class TestStaticCall {

    @Test
    public void sin() throws NoSuchMethodException {
        ParameterExpression x = Expressions.parameter(double.class);
        FuncDD r = Expressions.complie(FuncDD.class,
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
        FuncStringObjectsString r = Expressions.complie(FuncStringObjectsString.class,
            Expressions.call(
                String.class.getMethod("format", String.class, Object[].class),
                Expressions.constant("%d%s"),
                Expressions.constant(1),
                Expressions.constant("ss")
            ),
        x, y);
        Assert.assertEquals(String.format("%d%s",1, "ss"), r.invoke("%d%s", 1, "ss"));
    }

}
