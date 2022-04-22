package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;

public class StudentState extends ContextAdapter {
    public StudentState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insert(Object item) {
        return data.insert(item);
    }

    @Override
    public <T> boolean remove(T id) {
        return data.remove(id,Student.class);
    }

    @Override
    public String querying() {
        return data.querying(Student.class);
    }

    @Override
    public void readFromFile(String filePath) {
        data.readCVS(filePath, Student.class);
    }

    @Override
    public boolean back() {
        changeState(EState.CONFIGURATION);
        return true;
    }

    @Override
    public boolean isPhaseLock() {
        return false;
    }

    @Override
    public EState getState() {
        return EState.STUDENT;
    }
}
