import org.c41.expression4j.Expressions;
import org.c41.expression4j.Parameter;

public class TestMain {

    public static interface Run{
        int run(int x, int y);
    }

    public static void main(String[] args){
        Parameter x = Expressions.parameter(int.class);
        Parameter y = Expressions.parameter(int.class);
        Run r = Expressions.complie(Run.class, Expressions.add(x, y), x, y);
        System.out.println(r.run(100, 7));
    }

}
