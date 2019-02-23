interface Func<T1, T2, R>{
    R invoke(T1 a ,T2 b);
}

class Test implements Func<String, String, String>{

    @Override
    public String invoke(String x, String y){
        return null;
    }
}

/*
var t = x + y;
Syste.out.println(t)
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException {
        Func<String, String, String> f =  new Test();
        f.invoke(null, null);
    }

    public void run(StringBuilder sb){
        sb.append("before");
        try{
            sb.append("try1");
        }
        catch(RuntimeException e){
            sb.append("catch1");
        }
        finally{
            sb.append("finally");
        }
        sb.append("after");
    }

}
