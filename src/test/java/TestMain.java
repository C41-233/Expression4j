import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;

import java.io.PrintStream;

interface Run{
    void invoke();
}

/*
var t = x + y;
Syste.out.println(t)
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException {
        ParameterExpression x = Expressions.Parameter(int.class);
        Run r = Expressions.Compile(Run.class,
            Expressions.For(
                Expressions.Assign(x, Expressions.Constant(10)),
                Expressions.Greater(x, Expressions.Constant(0)),
                Expressions.Assign(x, Expressions.Subtract(x, Expressions.Constant(1))),
                Expressions.Call(
                    Expressions.Field(System.class, "out"),
                    PrintStream.class.getMethod("println", int.class),
                    x
                )
            )
        );
        r.invoke();
    }

}
