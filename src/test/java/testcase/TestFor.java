package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;

public class TestFor {

    @Test
    public void sum(){
        ParameterExpression sum = Expressions.Parameter(int.class);
        ParameterExpression i = Expressions.Parameter(int.class);
        FuncI r = Expressions.Compile(FuncI.class,
            Expressions.Block(
                Expressions.Assign(sum, Expressions.Constant(0)),
                Expressions.For(
                    Expressions.Assign(i, Expressions.Constant(10)),
                    Expressions.Greater(i, Expressions.Constant(0)),
                    Expressions.Assign(i, Expressions.Subtract(i, Expressions.Constant(1))),
                    Expressions.Assign(sum, Expressions.Add(sum, i))
                ),
                Expressions.Return(sum)
            )
        );
        r.invoke();
        Assert.assertEquals(10+9+8+7+6+5+4+3+2+1, r.invoke());
    }

    @Test
    public void sumBreak(){
        ParameterExpression sum = Expressions.Parameter(int.class);
        ParameterExpression i = Expressions.Parameter(int.class);
        FuncI r = Expressions.Compile(FuncI.class,
            Expressions.Block(
                Expressions.Assign(sum, Expressions.Constant(0)),
                Expressions.For(
                    Expressions.Assign(i, Expressions.Constant(10)),
                    Expressions.Greater(i, Expressions.Constant(0)),
                    Expressions.Assign(i, Expressions.Subtract(i, Expressions.Constant(1))),
                    Expressions.Assign(sum, Expressions.Add(sum, i)),
                    Expressions.Break()
                ),
                Expressions.Return(sum)
            )
        );
        r.invoke();
        Assert.assertEquals(10, r.invoke());
    }

    @Test
    public void sumBreakWithFinally(){
        ParameterExpression sum = Expressions.Parameter(int.class);
        ParameterExpression i = Expressions.Parameter(int.class);
        FuncI r = Expressions.Compile(FuncI.class,
            Expressions.Block(
                Expressions.Assign(sum, Expressions.Constant(0)),
                Expressions.For(
                    Expressions.Assign(i, Expressions.Constant(10)),
                    Expressions.Greater(i, Expressions.Constant(0)),
                    Expressions.Assign(i, Expressions.Subtract(i, Expressions.Constant(1))),
                    Expressions.TryFinally(
                        Expressions.Break(),
                        Expressions.Assign(sum, Expressions.Constant(100))
                    )
                ),
                Expressions.Return(sum)
            )
        );
        r.invoke();
        Assert.assertEquals(100, r.invoke());
    }

}
