interface Run{
    void invoke();
}

/*
var t = x + y;
Syste.out.println(t)
 */
public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException {
        for(int i=0; i<10; i++){
            try{
                break;
            }
            finally {
                System.out.println("yyy");
            }
        }
    }

    private static int run(int[] a){
        int x = a[2] = 4;
        return x + 5;
    }

}
