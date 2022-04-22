package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

public class ProposalState extends ContextAdapter{
    public ProposalState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public boolean isPhaseLock() {
        return data.isPhaseLock(EState.PROPOSALS);
    }

    @Override
    public void manualProposalAttribution(String proposalID, long studentID) {
        data.manualProposalAttribution(proposalID,studentID);
    }

    @Override
    public void manualProposalRemoveAttribution(String proposalID) {
        data.manualProposalRemoveAttribution(proposalID);
    }

    @Override
    public EState getState() {
        return EState.PROPOSALS;
    }
}
