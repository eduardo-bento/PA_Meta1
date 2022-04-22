package pt.isec.pa.apoio_poe.utils;

import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

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

    public static List<Object> readFile(String filePath,Class<?> type){
        List<Object> data = new ArrayList<>();
        List<Object> items = new ArrayList<>();

        Field[] myClass = type.getDeclaredFields();
        Field[] superClassFields = type.getSuperclass().getDeclaredFields();
        List<Field> fields = new ArrayList<>(myClass.length + superClassFields.length);
        Collections.addAll(fields,superClassFields); Collections.addAll(fields,myClass);

        try (Scanner input = new Scanner(new FileReader(filePath))){
            input.useDelimiter(",");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                for (Field field : fields){
                    String typeName = Utils.splitString(field.getType().getName(),"\\.");
                    if (field.getName().charAt(0) != '_'){
                        System.out.println(typeName);
                        if (typeName.equals("String")){
                            data.add(Scanner.class.getMethod("next").invoke(input));
                        } else{
                            data.add(Scanner.class.getMethod("next" + Utils.splitString(field.getType().getName(),"\\.")).invoke(input));
                        }
                        System.out.println(typeName);
                    }
                }
                //EDataStructures management = EDataStructures.fromClass(Student.class);
                //items.add(management.factory(data));
                data.clear();
            }
        }  catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

}
