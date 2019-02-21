import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;

interface BinaryInt {
    public int invoke(int x, int y);
}
public class TestMain {

    public static void main(String[] args) throws Exception {

        ParameterExpression x = Expressions.parameter(int.class);
        ParameterExpression y = Expressions.parameter(int.class);
        BinaryInt r = Expressions.complie(BinaryInt.class,
                Expressions.add(x, y),
                x, y);
    }

}
