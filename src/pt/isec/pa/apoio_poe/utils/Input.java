package pt.isec.pa.apoio_poe.utils;

import pt.isec.pa.apoio_poe.data.Commands;
import pt.isec.pa.apoio_poe.fsm.EState;

import java.lang.reflect.Field;
import java.util.*;

public final class Input {
    private Input() {}

    private static Scanner sc;

    static {
        resetScanner();
    }

    public static void resetScanner() {
        sc = new Scanner(System.in);
    }

    public static String readString(String title,boolean onlyOneWord) {
        String value;
        do {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            value = sc.nextLine().trim();
        } while (value.isBlank());
        if (onlyOneWord) {
            Scanner auxsc = new Scanner(value);
            value = auxsc.next();
        }
        return value;
    }

    public static int readInt(String title) {
        while (true) {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            if (sc.hasNextInt()) {
                int intValue = sc.nextInt();
                sc.nextLine();
                return intValue;
            } else
                sc.nextLine();
        }
    }

    public static long readLong(String title) {
        while (true) {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            if (sc.hasNextLong()) {
                long longValue = sc.nextLong();
                sc.nextLine();
                return longValue;
            } else
                sc.nextLine();
        }
    }

    public static boolean readBoolean(String title) {
        while (true) {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            return chooseOption(null, "true", "false") == 1;
        }
    }

    public static double readDouble(String title) {
        while (true) {
            if (title != null)
                System.out.print(title);
            else
                System.out.print("> ");
            if (sc.hasNextDouble()) {
                double doubleValue = sc.nextDouble();
                sc.nextLine();
                return doubleValue;
            } else
                sc.nextLine();
        }
    }

    public static int chooseOption(String title, String ... options) {
        int option = -1;
        do {
            if (title != null)
                System.out.println(System.lineSeparator()+title);
            System.out.println();
            for(int i = 0; i < options.length; i++) {
                System.out.printf("%3d - %s\n",i+1,options[i]);
            }
            System.out.print("\nOption: ");
            if (sc.hasNextInt())
                option = sc.nextInt();
            sc.nextLine();
        } while (option < 1 || option > options.length);
        return option;
    }

    public static List<Integer> chooseMultipleOptions(String title, String ... options) {
        String option = null;
        boolean dontPrint = false;
        List<Integer> values = new ArrayList<>();
        do {
            if (!dontPrint){
                if (title != null)
                    System.out.println(System.lineSeparator()+title);
                System.out.println();
                for(int i = 0; i < options.length; i++) {
                    System.out.printf("%3d - %s\n",i+1,options[i]);
                }
                dontPrint = true;
            }
            System.out.print("\nOption (ex: 1,3): ");
            option = sc.nextLine().trim();
            String[] value = option.split(",");
            for (String v : value){
                try{
                    int add = Integer.parseInt(v);
                    if (add < 0 || add > options.length || values.contains(add)){
                        values.clear();
                        continue;
                    }
                    values.add(add);
                }catch (NumberFormatException e){
                    values.clear();
                    continue;
                }
            }
        } while (option.isBlank() || values.isEmpty());
        return values;
    }

    public static int chooseOption(String title, List<String> options) {
        int option = -1;
        do {
            if (title != null)
                System.out.println(System.lineSeparator()+title);
            System.out.println();
            for(int i = 0; i < options.size(); i++) {
                System.out.printf("%3d - %s\n",i+1,options.get(i));
            }
            System.out.print("\nOption: ");
            if (sc.hasNextInt())
                option = sc.nextInt();
            sc.nextLine();
        } while (option < 1 || option > options.size());
        return option;
    }

    public static Object readClass(EState state){
        Commands commands = new Commands();
        List<Object> data = new ArrayList<>();
        List<String> info;
        Field[] myClass;
        Field[] superClassFields;

        myClass = state.getStructureClass().getDeclaredFields();
        superClassFields = state.getStructureClass().getSuperclass().getDeclaredFields();
        info = commands.getInfo(state.getStructureClass());

        List<Field> fields = new ArrayList<>(myClass.length + superClassFields.length);
        Collections.addAll(fields,superClassFields);
        Collections.addAll(fields,myClass);
        for (int i = 0,j = 0; i < info.size() ;i++,j++){
            if (fields.get(j).getName().charAt(0) == '_') j++;
            String typeName = Utils.splitString(fields.get(j).getType().getName(),"\\.");
            data.add(Utils.invokeMethod(typeName,typeName + " " + info.get(i),Input.class));
        }

        return state.factory(data);
    }
}