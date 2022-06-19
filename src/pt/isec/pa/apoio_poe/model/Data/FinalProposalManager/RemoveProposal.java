package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;

public class RemoveProposal extends CommandAdapter{
    String proposalID;
    long studentID;

    public RemoveProposal(FinalProposalManager receiver, String proposalID) {
        super(receiver);
        this.proposalID = proposalID;
    }

    @Override
    public boolean execute() {
        Proposal proposal = receiver.find(proposalID, Proposal.class);
        if (proposal != null){
            studentID = proposal.getStudent();
        }
        return receiver.manuelRemove_(proposalID);
    }

    public boolean undo() {
        return receiver.manualAttribution_(proposalID,studentID);
    }
}
