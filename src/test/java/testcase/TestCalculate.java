package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

public class TestCalculate {

    @Test
    public void intAdd(){
        ParameterExpression x = Expressions.Parameter(int.class);
        ParameterExpression y = Expressions.Parameter(int.class);
        FuncIII r = Expressions.Compile(FuncIII.class,
            Expressions.Add(x, y),
        x, y);
        Assert.assertEquals(300, r.invoke(100, 200));
        Assert.assertEquals(2, r.invoke(0, 2));
    }

    @Test
    public void intSub(){
        ParameterExpression x = Expressions.Parameter(int.class);
        ParameterExpression y = Expressions.Parameter(int.class);
        FuncIII r = Expressions.Compile(FuncIII.class,
            Expressions.Subtract(x, y),
        x, y);
        Assert.assertEquals(-100, r.invoke(100, 200));
        Assert.assertEquals(100, r.invoke(200, 100));
    }

    @Test
    public void intAddSub(){
        ParameterExpression x = Expressions.Parameter(int.class);
        ParameterExpression y = Expressions.Parameter(int.class);
        FuncIII r = Expressions.Compile(FuncIII.class,
                Expressions.Add(x, Expressions.Subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

    @Test
    public void longAddSub(){
        ParameterExpression x = Expressions.Parameter(long.class);
        ParameterExpression y = Expressions.Parameter(long.class);
        FuncLLL r = Expressions.Compile(FuncLLL.class,
                Expressions.Add(x, Expressions.Subtract(x, y)),
                x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

    @Test
    public void intLongAdd(){
        ParameterExpression x = Expressions.Parameter(int.class);
        ParameterExpression y = Expressions.Parameter(long.class);
        FuncILI r = Expressions.Compile(FuncILI.class,
                Expressions.Cast(Expressions.Add(x, Expressions.Subtract(x, y)), int.class),
        x, y);
        Assert.assertEquals(100 + 100 - 200 , r.invoke(100, 200));
        Assert.assertEquals(200 + 200 - 100, r.invoke(200, 100));
    }

} 
