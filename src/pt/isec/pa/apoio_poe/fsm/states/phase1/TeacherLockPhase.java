package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

import java.util.List;

public class TeacherLockPhase extends ContextAdapter {
    public TeacherLockPhase(Context context, Data data) {
        super(context, data);
    }

    @Override
    public List<Object> querying() {
        return data.querying(Teacher.class);
    }

    @Override
    public boolean back() {
        changeState(EState.CONFIGURATION_PHASE_LOCK);
        return true;
    }

    @Override
    public EState getState() {
        return EState.TEACHER_LOCK;
    }
}
