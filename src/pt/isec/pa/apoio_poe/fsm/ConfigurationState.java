package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.data.Student;
import pt.isec.pa.apoio_poe.data.Teacher;

public class ConfigurationState extends ContextAdapter {
    private EManagement management;
    public ConfigurationState(Context context, Data data) {
        super(context, data);
        management = EManagement.STUDENTS;
    }

    @Override
    public void changeManagementMode(EManagement management) {
        this.management = management;
    }

    @Override
    public boolean insert(Object object) {
        return switch (management){
            case STUDENTS :{
                if (data.addStudent((Student) object)){
                    Log.getInstance().addMessage("Student added");
                    yield true;
                }
                Log.getInstance().addMessage("Could not remove -> student with that id");
                yield  false;
            }
            case TEACHER:{
                if (data.addTeacher((Teacher) object)){
                    Log.getInstance().addMessage("Teacher added");
                    yield true;
                }
                Log.getInstance().addMessage("Could not remove -> student with that name");
                yield  false;
            }
            case PROJECT_STAGE: {
                yield true;
            }
        };
    }

    @Override
    public <T,K> boolean edit(T entity,K value,String label) {
        if (management == EManagement.STUDENTS) {
            long id = (long) entity;
            if(data.findStudent(id)){
                data.editStudent(id,value,label);
            }
        } else if (management == EManagement.TEACHER) {
            String id = (String) entity;
            if(data.findTeacher(id)){
                data.editTeacher(id,value,label);
            }
        }
        return true;
    }

    @Override
    public void closePhase() {
        data.lockPhase(getState());
    }

    @Override
    public <T> boolean remove(T entity) {
        return switch (management){
            case STUDENTS:{
                long id = (long) entity;
                if (data.findStudent(id)){
                    data.removeStudent(id);
                    yield true;
                }
            }
            case TEACHER:{
                String email = (String) entity;
                if (data.findTeacher(email)){
                    data.removeTeacher(email);
                    yield true;
                }
            }
            case PROJECT_STAGE:{
                yield true;
            }
            default:{
                yield false;
            }
        };
    }

    @Override
    public void goCandidacy() {
        changeState(EState.CANDIDACY);
    }

    @Override
    public EState getState() {
        return EState.CONFIGURATION;
    }
}
