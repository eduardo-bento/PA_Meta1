package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.data.Proposals.InterShip;

import java.util.Arrays;
import java.util.List;

public enum EManagement {
    STUDENTS,
    TEACHER,
    INTERSHIP,
    PROJECT;

    public static EManagement fromInteger(int x) {
        return switch(x) {
            case 0 -> STUDENTS;
            case 1 -> TEACHER;
            case 2 -> INTERSHIP;
            case 3 -> PROJECT;
            default -> null;
        };
    }

    public Class getDataClass(){
        return switch (this){
            case STUDENTS -> Student.class;
            case TEACHER -> Teacher.class;
            case INTERSHIP -> InterShip.class;
            default -> null;
        };
    }

    public Object factory(List<Object> data){
        return switch (this){
            case STUDENTS ->
                new Student((long) data.get(0),(String) data.get(1),
                        (String) data.get(2),(String) data.get(3),
                        (String) data.get(4),(double) data.get(5),
                        (boolean) data.get(6));
            case TEACHER ->  new Teacher((String) data.get(0), (String) data.get(1), (Boolean) data.get(2));
            case INTERSHIP -> new InterShip((String) data.get(0), (String) data.get(1),
                    (String) data.get(2), (String) data.get(3), (Long) data.get(4));
            default -> null;
        };
    }

    public static List<String> getTypes() {
        return Arrays.asList("Student","Teacher","InterShip","Project");
    }
}
