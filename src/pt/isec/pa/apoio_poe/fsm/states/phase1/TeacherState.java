package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

public class TeacherState extends ContextAdapter {
    public TeacherState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void readFromFile(String filePath) {
        data.readCSV(filePath,Teacher.class);
    }

    @Override
    public String querying() {
        return data.querying(Teacher.class);
    }

    @Override
    public boolean back() {
        changeState(EState.CONFIGURATION_PHASE);
        return true;
    }

    @Override
    public EState getState() {
        return EState.TEACHER;
    }
}
