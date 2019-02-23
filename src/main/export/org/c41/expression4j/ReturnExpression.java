package org.c41.expression4j;

public class ReturnExpression extends Expression{

    private final Expression expression;

    ReturnExpression(){
        this(null);
    }

    ReturnExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    void emit(BodyEmit ctx) {
        if(expression != null){
            expression.emit(ctx);
            ctx.ret(expression.getExpressionType());
        }
        else{
            ctx.ret();
        }
    }

    @Override
    public Class<?> getExpressionType() {
        return null;
    }
}
