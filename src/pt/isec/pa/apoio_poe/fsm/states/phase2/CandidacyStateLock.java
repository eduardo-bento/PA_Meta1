package pt.isec.pa.apoio_poe.fsm.states.phase2;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy;

public class CandidacyStateLock extends ContextAdapter {
    public CandidacyStateLock(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void forward() {
        changeState(EState.PROPOSALS_PHASE);
    }

    @Override
    public boolean back() {
        if (data.isPhaseLock(0)){
            changeState(EState.CONFIGURATION_PHASE_LOCK);
        }
        changeState(EState.CONFIGURATION_PHASE);
        return true;
    }

    @Override
    public String querying() {
        return data.querying(Candidacy.class);
    }

    @Override
    public EState getState() {
        return EState.CANDIDACY_PHASE_LOCK;
    }
}
