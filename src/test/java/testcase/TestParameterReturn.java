package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

public class TestParameterReturn {

    @Test
    public void test1(){
        ParameterExpression x = Expressions.Parameter(int.class);
        ParameterExpression y = Expressions.Parameter(int.class);
        FuncIII r = Expressions.Compile(FuncIII.class,
            x,
        x, y);
        Assert.assertEquals(100, r.invoke(100, 200));
        Assert.assertEquals(200, r.invoke(200, 100));
    }

    @Test
    public void test2(){
        ParameterExpression x = Expressions.Parameter(int.class);
        ParameterExpression y = Expressions.Parameter(int.class);
        FuncIII r = Expressions.Compile(FuncIII.class,
                y,
        x, y);
        Assert.assertEquals(200, r.invoke(100, 200));
        Assert.assertEquals(100, r.invoke(200, 100));
    }

} 
