package com.jewel.ergon.jobs.utilis;

import com.jewel.ergon.jobs.exceptions.IncompatibleSourceAndTargetFieldsTypesException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


//TODO test this method (critical)
public class MappingData {

    private static final int CACHE_LIMIT = 100;
    private static final Map<Class<?>, Field[]> fieldCache = new LinkedHashMap<>(CACHE_LIMIT, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Class<?>, Field[]> eldest) {
            return this.size() > CACHE_LIMIT;
        }
    };


    /**
     * A method useful for updating an entity in jpa, it gets value from a source object field ,
     * and set it in the target object field that have the same name and type
     *
     * @param source source object to get values from
     * @param target target object to set values into
     * @param <S>    type of the source
     * @param <T>    type of the target
     * @throws IllegalArgumentException thrown if source or target are null
     * @throws IllegalAccessException   thrown if source or target are null
     */
    public static <S, T> void getAndSet(S source, T target) throws IllegalAccessException, IllegalArgumentException, IncompatibleSourceAndTargetFieldsTypesException {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source or target cannot be null");
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        // Retrieve or cache source and target fields
        Field[] sourceFields = fieldCache.computeIfAbsent(sourceClass, MappingData::getAllFields);
        Field[] targetFields = fieldCache.computeIfAbsent(targetClass, MappingData::getAllFields);

        Map<String, Field> sourceFieldMap = new HashMap<>();
        for (Field sourceField : sourceFields) {
            sourceFieldMap.put(sourceField.getName(), sourceField);
        }

        // Iterate over target fields, mapping only fields that match by name and type
        for (Field targetField : targetFields) {
            Field sourceField = sourceFieldMap.get(targetField.getName());
            if (!targetField.getType().isAssignableFrom(sourceField.getType()))
                throw new IncompatibleSourceAndTargetFieldsTypesException("incompatible type for field %s  of type %s ".formatted(targetField, targetField.getType()));
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            Object value = sourceField.get(source);
            targetField.set(target, value);
        }
    }

    private static Field[] getAllFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.putIfAbsent(field.getName(), field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields.values().toArray(new Field[0]);
    }


//    public static <T> void getAndSet(T source, T target) throws IllegalAccessException {
//
//        if (source == null || target == null) throw new IllegalArgumentException();
//
//        Field[] fields = target.getClass().getDeclaredFields();
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Object value = field.get(source);
//            field.set(target, value);
//        }
//
//
//    }
}
