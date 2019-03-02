import java.io.IOException;

interface Run{
    void invoke();
}

/*
var t = x + y;
Syste.out.println(t)
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException {
        try{
            throw new IOException();
        }
        catch (IOException e){
            throw new RuntimeException();
        }
        finally {
            System.out.println("123");
        }
    }

    private static int run(int[] a){
        int x = a[2] = 4;
        return x + 5;
    }

}
