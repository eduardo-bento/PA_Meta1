package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Teacher;
import pt.isec.pa.apoio_poe.model.TeacherOrder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class TeacherManager extends Manager<Teacher> {
    public TeacherManager(Data data) {
        super(data);
    }

    public float average(){
        float count = 0;
        for (Teacher teacher : list){
            count += teacher.getAmount();
        }
        return count/list.size();
    }

    public int highest(){
        Teacher teacher = Collections.max(list,new TeacherOrder());
        return teacher.getAmount();
    }

    public int lowest(){
        Teacher teacher = Collections.min(list,new TeacherOrder());
        return teacher.getAmount();
    }
}
