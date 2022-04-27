package pt.isec.pa.apoio_poe.model.Student;

import java.util.Comparator;

public class StudentClassification implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Double.compare(o1.getClassification(),o2.getClassification());
    }
}
