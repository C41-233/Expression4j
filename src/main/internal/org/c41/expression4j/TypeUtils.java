package org.c41.expression4j;

final class TypeUtils {

    public static boolean isIntType(Class<?> type){
        return type == int.class;
    }

    public static int getSlotCount(Class<?> type) {
        return type == long.class || type == double.class ? 2 : 1;
    }
}
