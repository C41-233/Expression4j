import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import types.Blob;
import types.BlobSetter;

public class TestMain {

    public static void main(String[] args) throws Exception {

        ParameterExpression x = Expressions.parameter(Blob.class);
        ParameterExpression y = Expressions.parameter(int.class);
        BlobSetter r = Expressions.complie(BlobSetter.class,
                Expressions.assign(
                        Expressions.field(x, Blob.class.getField("value")),
                        y
                ),
                x, y);
    }

}
