package pt.isec.pa.apoio_poe.fsm.states.phase2;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;

import java.util.List;

public class CandidacyStateLock extends ContextAdapter {
    public CandidacyStateLock(Context context, Data data) {
        super(context, data);
    }

    @Override
    public boolean back() {
        if (!data.isPhaseLock(0)){
            changeState(EState.CONFIGURATION_PHASE);
            return true;
        }
        changeState(EState.CONFIGURATION_PHASE_LOCK);
        return true;
    }

    @Override
    public void forward() {
        if (!data.isPhaseLock(2)){
            changeState(EState.PROPOSALS_PHASE);
            return;
        }
        changeState(EState.PROPOSALS_PHASE_LOCK);
    }

    @Override
    public String getListOfStudents() {
        return data.getListOfStudents_CandidacyPhase();
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return data.getListProposals_CandidacyPhase(filters);
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
