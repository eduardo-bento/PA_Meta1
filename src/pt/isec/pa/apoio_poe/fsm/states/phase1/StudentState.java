package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;

import java.util.List;

public class StudentState extends ContextAdapter {
    public StudentState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public <T> boolean remove(T id) {
        return data.remove(id,Student.class);
    }

    @Override
    public List<Object> querying() {
        return data.querying(Student.class);
    }

    @Override
    public void readFromFile(String filePath) {
        data.readCSV(filePath, Student.class);
    }

    @Override
    public boolean back() {
        changeState(EState.CONFIGURATION_PHASE);
        return true;
    }

    @Override
    public EState getState() {
        return EState.STUDENT;
    }
}
