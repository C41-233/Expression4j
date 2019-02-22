import org.c41.expression4j.Expressions;

interface Run {
    public void run();
}

/*
var t = x + y;
Syste.out.println(t)
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException {
        Run r = Expressions.compile(Run.class, Expressions.tryCatchFinally(
            Expressions.call(TestMain.class.getMethod("runTry")),
            Expressions.call(TestMain.class.getMethod("runFinally")),
            Expressions.catchBlock(Exception.class, Expressions.call(TestMain.class.getMethod("runCatch")))
        ));
        r.run();
    }

    public static void runTry() throws Exception{
        System.out.println("try");
    }

    public static void runCatch(){
        System.out.println("catch");
    }

    public static void runFinally(){
        System.out.println("finally");
    }

}
