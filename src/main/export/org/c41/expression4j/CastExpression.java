package org.c41.expression4j;

import java.lang.reflect.Method;

public class CastExpression extends Expression{

    private final Expression expression;
    private final Class<?> leftType;

    CastExpression(Expression expression, Class<?> type){
        this.expression = expression;
        this.leftType = type;
    }

    @Override
    void emitRead(BodyEmit ctx) {
        expression.emitRead(ctx);
        StackType rightStackType = TypeUtils.getStackType(expression.getExpressionType());
        StackType leftStackType = TypeUtils.getStackType(leftType);

        if(leftType == long.class){
            if(rightStackType == StackType.Int){
                ctx.i2l();
                return;
            }
        }
        else if(leftType == int.class){
            if(rightStackType == StackType.Long){
                ctx.l2i();
                return;
            }
        }
        else if(leftStackType == StackType.Reference){
            if(rightStackType == StackType.Reference){
                return;
            }
            else if(leftType.isAssignableFrom(Integer.class)){
                if(rightStackType == StackType.Int){
                    try {
                        Method method = Integer.class.getMethod("valueOf", int.class);
                        ctx.invokestatic(method);
                        return;
                    } catch (NoSuchMethodException e) {
                        throw Error.emitFail("cannot find Integer.valueOf", e);
                    }
                }
            }
        }
        throw Error.badCast(expression.getExpressionType(), leftType);
    }

    @Override
    public Class<?> getExpressionType() {
        return leftType;
    }
}
