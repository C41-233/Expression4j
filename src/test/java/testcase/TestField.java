package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.Parameter;
import org.junit.Assert;
import org.junit.Test;
import types.Blob;
import types.Blob2Int;

public class TestField {

    @Test
    public void test1() throws NoSuchFieldException {
        Parameter x = Expressions.parameter(Blob.class);
        Blob2Int r = Expressions.complie(Blob2Int.class,
            Expressions.field(x, Blob.class.getField("value")),
        x);
        Assert.assertEquals(0, r.invoke(new Blob()));
        Assert.assertEquals(500, r.invoke(new Blob(500)));
    }

}
