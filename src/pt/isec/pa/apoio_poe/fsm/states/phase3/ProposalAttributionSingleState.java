package pt.isec.pa.apoio_poe.fsm.states.phase3;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

import java.util.List;

public class ProposalAttributionSingleState extends ContextAdapter {
    public ProposalAttributionSingleState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void automaticAssignmentForProjectAndInterShip() {
        data.automaticAssignmentForProjectAndInterShip();
    }

    @Override
    public String getListOfStudents() {
        return data.getListOfStudentsFinal();
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return data.getListProposalsFinal(filters);
    }

    @Override
    public void forward() {
        changeState(EState.TEACHER_ATTRIBUTION_PHASE);
    }

    @Override
    public boolean back() {
        if (!data.isPhaseLock(1)){
            changeState(EState.CANDIDACY);
            return true;
        }
        changeState(EState.CANDIDACY_PHASE_LOCK);
        return true;
    }

    @Override
    public EState getState() {
        return EState.PROPOSAL_PHASE_SINGLE;
    }
}
