package pt.isec.pa.apoio_poe.fsm.states.phase3;

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
        if(!data.automaticAttribution()){
            changeState(EState.TIEBREAKER);
            return;
        }
        changeState(EState.PROPOSALS_PHASE);
    }

    @Override
    public void automaticAssignmentForProjectAndInterShip() {
        data.automaticAssignmentForProjectAndInterShip();
    }


    @Override
    public void manualAttribution(String proposal_id, long student_id) {
        data.manualProposalAttribution(proposal_id,student_id);
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
            return true;
        }
        changeState(EState.CANDIDACY_PHASE_LOCK);
        return true;
    }

    @Override
    public boolean closePhase() {
        if (data.lockProposalPhase()){
            data.lockPhase(2);
            changeState(EState.TEACHER_ATTRIBUTION_PHASE);
            return true;
        }
        changeState(EState.PROPOSALS_PHASE);
        return false;
    }

    @Override
    public void exportFile(String filePath) {
        data.exportPhase3(filePath);
    }

    @Override
    public EState getState() {
        return EState.PROPOSALS_PHASE;
    }
}
