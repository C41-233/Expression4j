package org.c41.expression4j;

final class BinaryLift {

    public final Expression Left;
    public final Expression Right;

    public final Expression LiftLeft;
    public final Expression LiftRight;
    public final Class<?> LiftType;

    BinaryLift(Expression left, Expression right){
        this.Left = left;
        this.Right = right;

        StackType leftType = TypeUtils.getStackType(left.getExpressionType());
        StackType rightType = TypeUtils.getStackType(right.getExpressionType());

        if(leftType == StackType.Long){
            switch (rightType) {
                case Int:
                    this.LiftLeft = left;
                    this.LiftRight = Expressions.Cast(right, long.class);
                    this.LiftType = long.class;
                    return;
                case Long:
                    this.LiftLeft = left;
                    this.LiftRight = right;
                    this.LiftType = long.class;
                    return;
            }
        }
        else if(leftType == StackType.Int){
            switch (rightType) {
                case Int:
                    this.LiftLeft = left;
                    this.LiftRight = right;
                    this.LiftType = int.class;
                    return;
                case Long:
                    this.LiftLeft = Expressions.Cast(left, long.class);
                    this.LiftRight = right;
                    this.LiftType = long.class;
                    return;
            }
        }
        throw Error.badOperator();
    }

}
