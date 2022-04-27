package pt.isec.pa.apoio_poe.fsm.states;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

import java.util.List;

public class ProposalAttributionState extends ContextAdapter {
    public ProposalAttributionState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void automaticAttributionWithoutAssociation() {
        data.automaticAttribution();
    }

    @Override
    public void automaticAttributionForProposalsWithStudent() {
        data.automaticAttributionForProposalsWithStudent();
    }

    @Override
    public void manualAttribution(String proposalID, long studentID) {
        data.manualProposalAttribution(proposalID,studentID);
    }

    @Override
    public void manualRemove(String proposalID) {
        data.manualProposalRemoveAttribution(proposalID);
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
        }
        changeState(EState.PROPOSALS_PHASE);
        return false;
    }

    @Override
    public boolean closePhase() {
        if (data.lockProposalPhase()){
            changeState(EState.TEACHER_ATTRIBUTION_PHASE);
            return true;
        }
        changeState(EState.PROPOSALS_PHASE);
        return false;
    }

    @Override
    public EState getState() {
        return EState.PROPOSALS_PHASE;
    }
}
