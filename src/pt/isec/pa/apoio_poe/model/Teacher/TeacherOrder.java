package pt.isec.pa.apoio_poe.model.Teacher;

import java.util.Comparator;

public class TeacherOrder implements Comparator<Teacher> {
    @Override
    public int compare(Teacher o1, Teacher o2) {
        return o1.getAmount() - o2.getAmount();
    }
}
