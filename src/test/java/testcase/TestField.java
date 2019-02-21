package testcase;

import org.c41.expression4j.Expression;
import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;
import types.Blob;
import types.Blob2Int;
import types.BlobSetter;

public class TestField {

    @Test
    public void test1() throws NoSuchFieldException {
        ParameterExpression x = Expressions.parameter(Blob.class);
        Blob2Int r = Expressions.complie(Blob2Int.class,
            Expressions.field(x, Blob.class.getField("value")),
        x);
        Assert.assertEquals(0, r.invoke(new Blob()));
        Assert.assertEquals(500, r.invoke(new Blob(500)));
    }

    /*
        func Blob b, int x:
            b.value = x
     */
    @Test
    public void test2() throws NoSuchFieldException {
        ParameterExpression x = Expressions.parameter(Blob.class);
        ParameterExpression y = Expressions.parameter(int.class);
        BlobSetter r = Expressions.complie(BlobSetter.class,
            Expressions.assign(
                Expressions.field(x, Blob.class.getField("value")),
                y
            ),
        x, y);
        Blob b  = new Blob(100);
        r.invoke(b, 1);
        Assert.assertEquals(1, b.value);
        r.invoke(b, 2);
        Assert.assertEquals(2, b.value);
    }

}
