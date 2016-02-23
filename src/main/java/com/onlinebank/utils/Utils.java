package com.onlinebank.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.*;

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

        List<Field> fieldList = new ArrayList<Field>();

        Class<?> current = obj.getClass();
        while (current.getSuperclass() != null) {
            fieldList.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }

        for (Field field : fieldList) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null || (field.get(obj).getClass() == String.class && ((String) field.get(obj)).isEmpty())) {
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
        Class clazz = obj.getClass();

        while (clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(NotNull.class)) {
                    field.setAccessible(true);
                    try {
                        if (field.get(obj) == null || (field.get(obj).getClass() == String.class && ((String) field.get(obj)).isEmpty())) {
                            nullFields.add(field.getName());
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }

        return nullFields;
    }

    /**
     * Generate a random string to perform tests and avoid unique fields constraints
     *
     * @param rng
     * @param characters
     * @param length
     * @return
     */
    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     * BCrypt
     *
     * @return
     */
    public static String encryptPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
        /*
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] encryptedPassword = md5.digest(password.getBytes("UTF-8"));
            return (new HexBinaryAdapter()).marshal(encryptedPassword);
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null; */
    }

    /**
     * Days between two dates
     *
     * @param Date date1
     * @param Date date2
     * @return
     */
    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

}
