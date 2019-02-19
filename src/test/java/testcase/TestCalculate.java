package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.Parameter;
import org.junit.Assert;
import org.junit.Test;
import types.BinaryInt;

public class TestCalculate {

    @Test
    public void test1(){
        Parameter x = Expressions.parameter(int.class);
        Parameter y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
            Expressions.add(x, y),
        x, y);
        Assert.assertEquals(300, r.invoke(100, 200));
        Assert.assertEquals(2, r.invoke(0, 2));
    }

    @Test
    public void test2(){
        Parameter x = Expressions.parameter(int.class);
        Parameter y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
            Expressions.subtract(x, y),
        x, y);
        Assert.assertEquals(-100, r.invoke(100, 200));
        Assert.assertEquals(100, r.invoke(200, 100));
    }

    @Test
    public void test3(){
        Parameter x = Expressions.parameter(int.class);
        Parameter y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
                Expressions.add(x, Expressions.subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

} 
