package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

public class TestCalculate {

    @Test
    public void intAdd(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        FuncIII r = Expressions.complie(FuncIII.class,
            Expressions.add(x, y),
        x, y);
        Assert.assertEquals(300, r.invoke(100, 200));
        Assert.assertEquals(2, r.invoke(0, 2));
    }

    @Test
    public void intSub(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        FuncIII r = Expressions.complie(FuncIII.class,
            Expressions.subtract(x, y),
        x, y);
        Assert.assertEquals(-100, r.invoke(100, 200));
        Assert.assertEquals(100, r.invoke(200, 100));
    }

    @Test
    public void intAddSub(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        FuncIII r = Expressions.complie(FuncIII.class,
                Expressions.add(x, Expressions.subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

    @Test
    public void longAddSub(){
        ParameterExpression x = Expressions.parameter(long.class);
        ParameterExpression y = Expressions.parameter(long.class);
        FuncLLL r = Expressions.complie(FuncLLL.class,
                Expressions.add(x, Expressions.subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

    @Test
    public void intLongAdd(){
        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(long.class);
        FuncILI r = Expressions.complie(FuncILI.class,
                Expressions.cast(Expressions.add(x, Expressions.subtract(x, y)), int.class),
        x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

} 
