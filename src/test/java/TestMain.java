import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;

public class TestMain {

    public static class My{
        public int value = 5;
    }

    public static interface Run{
        int run(My x);
    }

    public static void main(String[] args) throws Exception {
        ParameterExpression x = Expressions.parameter(My.class);
        Run r = Expressions.complie(Run.class,
            Expressions.field(x, My.class.getField("value")),
        x);
        System.out.println(r.run(new My()));
    }

}
