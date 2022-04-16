package pt.isec.pa.apoio_poe.utils;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.model.Student;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static String splitString(String string,String c){
        String[] strings = string.split(c);
        return capitalString(strings[strings.length -1]);
    }

    public static String capitalString(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static Object invokeMethod(String type,String name,Class typeClass){
        try{
            if (type.equals("String")){
                Method method = typeClass.getMethod("read" + type,String.class,boolean.class);
                return method.invoke(null,name + ": ",false);
            } else{
                Method method = typeClass.getMethod("read" + type,String.class);
                return method.invoke(null,name + ": ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Field getFirstField(Class type){
        return type.getDeclaredFields()[0];
    }

    public static Class getSuperClass(Class type){
        return type.getSuperclass() != Object.class ? type.getSuperclass() : type;
    }
}
