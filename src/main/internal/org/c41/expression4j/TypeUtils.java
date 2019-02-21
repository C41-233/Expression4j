package org.c41.expression4j;

enum StackType{
    Int,
    Long,
    Float,
    Double,
    Reference,
}

final class TypeUtils {

    public static StackType getStackType(Class<?> type){
        if(type == int.class || type == short.class || type == byte.class || type == char.class || type == boolean.class){
            return StackType.Int;
        }
        else if(type == long.class){
            return StackType.Long;
        }
        else if(type == float.class){
            return StackType.Float;
        }
        else if(type == double.class){
            return StackType.Double;
        }
        else{
            return StackType.Reference;
        }
    }

    public static int getSlotCount(Class<?> type) {
        return type == long.class || type == double.class ? 2 : 1;
    }

}
