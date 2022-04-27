package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class ConfigurationStateLock extends ContextAdapter {
    public ConfigurationStateLock(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void goToMode(int option) {
        switch (option){
            case 1 -> changeState(EState.STUDENT_LOCK);
            case 2 -> changeState(EState.TEACHER_LOCK);
            case 3 -> changeState(EState.PROPOSAL_LOCK);
        }
    }

    @Override
    public void forward() {
        changeState(EState.CANDIDACY);
    }

    @Override
    public EState getState() {
        return EState.CONFIGURATION_PHASE_LOCK;
    }
}
