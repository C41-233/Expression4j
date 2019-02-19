package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.Parameter;
import org.junit.Assert;
import org.junit.Test;
import types.BinaryInt;

public class TestParameterReturn {

    @Test
    public void test1(){
        Parameter x = Expressions.parameter(int.class);
        Parameter y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
            x,
        x, y);
        Assert.assertEquals(100, r.invoke(100, 200));
        Assert.assertEquals(200, r.invoke(200, 100));
    }

    @Test
    public void test2(){
        Parameter x = Expressions.parameter(int.class);
        Parameter y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
                y,
        x, y);
        Assert.assertEquals(200, r.invoke(100, 200));
        Assert.assertEquals(100, r.invoke(200, 100));
    }

} 
