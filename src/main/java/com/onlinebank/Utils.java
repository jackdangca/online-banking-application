package com.onlinebank;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p0wontnx on 1/25/16.
 */
public class Utils {


    /**
     * Ensure that all object fields annotated with {@link NotNull} are not null
     *
     * @param obj
     * @return
     */
    public static boolean isModelFieldNull(Object obj) {

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
        return false;
    }

    /**
     * Get name of fields from object model which are annotated with {@link NotNull} and having a null value
     *
     * @param obj
     * @return
     */
    public static List<String> getModelNullFields(Object obj) {
        List<String> nullFields = new ArrayList<String>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        nullFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
        return nullFields;
    }

}
