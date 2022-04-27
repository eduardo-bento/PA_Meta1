package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Student;

public class ProposalLockPhase extends ContextAdapter {
    public ProposalLockPhase(Context context, Data data) {
        super(context, data);
    }

    @Override
    public String querying() {
        return data.querying(Student.class);
    }

    @Override
    public boolean back() {
        changeState(EState.CONFIGURATION_PHASE_LOCK);
        return true;
    }

    @Override
    public EState getState() {
        return EState.PROPOSAL_LOCK;
    }
}
