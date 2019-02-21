package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;
import types.BinaryInt;
import types.BinaryLong;

public class TestCalculate {

    @Test
    public void test1(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
            Expressions.add(x, y),
        x, y);
        Assert.assertEquals(300, r.invoke(100, 200));
        Assert.assertEquals(2, r.invoke(0, 2));
    }

    @Test
    public void test2(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
            Expressions.subtract(x, y),
        x, y);
        Assert.assertEquals(-100, r.invoke(100, 200));
        Assert.assertEquals(100, r.invoke(200, 100));
    }

    @Test
    public void test3(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
                Expressions.add(x, Expressions.subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

    @Test
    public void test4(){
        ParameterExpression x = Expressions.parameter(long.class);
        ParameterExpression y = Expressions.parameter(long.class);
        BinaryLong r = Expressions.complie(BinaryLong.class,
                Expressions.add(x, Expressions.subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

} 
