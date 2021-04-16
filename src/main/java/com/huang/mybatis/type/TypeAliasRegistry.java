package com.huang.mybatis.type;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.*;

/**
 * @author: hsz
 * @date: 2021/4/14 15:48
 * @description:
 */

public class TypeAliasRegistry {

    private final Map<String, Class<?>> typeAliases = new HashMap<>();

    public TypeAliasRegistry() {
        typeAliases.put("string", String.class);

        typeAliases.put("byte", Byte.class);
        typeAliases.put("long", Long.class);
        typeAliases.put("short", Short.class);
        typeAliases.put("int", Integer.class);
        typeAliases.put("integer", Integer.class);
        typeAliases.put("double", Double.class);
        typeAliases.put("float", Float.class);
        typeAliases.put("boolean", Boolean.class);

        typeAliases.put("byte[]", Byte[].class);
        typeAliases.put("long[]", Long[].class);
        typeAliases.put("short[]", Short[].class);
        typeAliases.put("int[]", Integer[].class);
        typeAliases.put("integer[]", Integer[].class);
        typeAliases.put("double[]", Double[].class);
        typeAliases.put("float[]", Float[].class);
        typeAliases.put("boolean[]", Boolean[].class);

        typeAliases.put("_byte", byte.class);
        typeAliases.put("_long", long.class);
        typeAliases.put("_short", short.class);
        typeAliases.put("_int", int.class);
        typeAliases.put("_integer", int.class);
        typeAliases.put("_double", double.class);
        typeAliases.put("_float", float.class);
        typeAliases.put("_boolean", boolean.class);

        typeAliases.put("_byte[]", byte[].class);
        typeAliases.put("_long[]", long[].class);
        typeAliases.put("_short[]", short[].class);
        typeAliases.put("_int[]", int[].class);
        typeAliases.put("_integer[]", int[].class);
        typeAliases.put("_double[]", double[].class);
        typeAliases.put("_float[]", float[].class);
        typeAliases.put("_boolean[]", boolean[].class);

        typeAliases.put("date", Date.class);
        typeAliases.put("decimal", BigDecimal.class);
        typeAliases.put("bigdecimal", BigDecimal.class);
        typeAliases.put("biginteger", BigInteger.class);
        typeAliases.put("object", Object.class);

        typeAliases.put("date[]", Date[].class);
        typeAliases.put("decimal[]", BigDecimal[].class);
        typeAliases.put("bigdecimal[]", BigDecimal[].class);
        typeAliases.put("biginteger[]", BigInteger[].class);
        typeAliases.put("object[]", Object[].class);

        typeAliases.put("map", Map.class);
        typeAliases.put("hashmap", HashMap.class);
        typeAliases.put("list", List.class);
        typeAliases.put("arraylist", ArrayList.class);
        typeAliases.put("collection", Collection.class);
        typeAliases.put("iterator", Iterator.class);

        typeAliases.put("ResultSet", ResultSet.class);
    }

    public <T> Class<T> resolveAlias(String alias) {
        try {
            if (alias == null) {
                return null;
            }
            String key = alias.toLowerCase(Locale.ENGLISH);
            Class<T> value;
            if (typeAliases.containsKey(key)) {
                value = (Class<T>) typeAliases.get(key);
            } else {
                value = (Class<T>) Class.forName(alias);
            }
            return value;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not resolve type alias '" + alias + "'.  Cause: " + e, e);
        }
    }
}
