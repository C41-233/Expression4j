import org.c41.expression4j.Expressions;

interface Run {
    public String run();
}

/*
var t = x + y;
Syste.out.println(t)
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException {
        run();
    }

    private static String run(){
        try{
            return "1";
        }
        finally {
            return "2";
        }
    }

}
