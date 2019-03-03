package testcase;

import org.c41.expression4j.Expressions;
import org.c41.expression4j.ParameterExpression;
import org.junit.Assert;
import org.junit.Test;
import testcase.template.TemplateLoader;

public class TestField {

    @Test
    public void testGet() throws NoSuchFieldException {
        ParameterExpression x = Expressions.Parameter(Blob.class);
        Blob2Int r = Expressions.Compile(Blob2Int.class,
            Expressions.Field(x, Blob.class.getField("value")),
        x);
        Assert.assertEquals(0, r.invoke(new Blob()));
        Assert.assertEquals(500, r.invoke(new Blob(500)));
    }

    /*
        func Blob b, int x:
            b.value = x
     */
    @Test
    public void testSet() throws NoSuchFieldException {
        ParameterExpression x = Expressions.Parameter(Blob.class, "x");
        ParameterExpression y = Expressions.Parameter(int.class, "y");
        BlobSetter r = Expressions.Compile(BlobSetter.class,
            Expressions.Assign(
                Expressions.Field(x, Blob.class.getField("value")),
                y
            ),
        x, y);
        Blob b  = new Blob(100);
        r.invoke(b, 1);
        Assert.assertEquals(1, b.value);
        r.invoke(b, 2);
        Assert.assertEquals(2, b.value);

        Assert.assertEquals(TemplateLoader.load("TestField.testSet"), r.toString());
    }

    /*
        func(Blob a, Blob b):
            var tmp = a.value;
            a.value = b.value;
            b.value = tmp;
     */
    @Test
    public void testSwap(){
        ParameterExpression a = Expressions.Parameter(Blob.class, "a");
        ParameterExpression b = Expressions.Parameter(Blob.class, "b");
        ParameterExpression tmp = Expressions.Parameter(int.class, "tmp");
        Action2<Blob, Blob> r = Expressions.Compile(
            Action2.class,
            Expressions.Block(
                Expressions.Assign(tmp, Expressions.Field(a, "value")),
                Expressions.Assign(
                    Expressions.Field(a, "value"),
                    Expressions.Field(b, "value")
                ),
                Expressions.Assign(Expressions.Field(b, "value"), tmp)
            ),
            a, b
        );
        Blob x = new Blob(100);
        Blob y = new Blob(200);
        Assert.assertEquals(100, x.value);
        Assert.assertEquals(200, y.value);
        r.invoke(x, y);
        Assert.assertEquals(200, x.value);
        Assert.assertEquals(100, y.value);

        Assert.assertEquals(
        "public void invoke(Blob a, Blob b){\n\tint tmp = a.value;\n\ta.value = b.value;\n\tb.value = tmp;\n}",
            r.toString()
        );
    }

}
