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

        test1();
        test2(r);
        test1();
        test2(r);
    }

    private static final int Times = 10000000;

    private static void test1(){
        long start = System.currentTimeMillis();
        for(int i = 0; i < Times; i++){
            try{
                runTry();
            }
            catch (Exception e){
                runCatch();
            }
            finally{
                runFinally();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static void test2(Run r){
        long start = System.currentTimeMillis();
        for(int i = 0; i < Times; i++){
            r.run();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void runTry() throws Exception{
        run(0);
    }

    public static void runCatch(){
        run(10);
    }

    public static void runFinally(){
        run(20);
    }

    private static void run(int i){
        Math.sin(i);
    }

}
