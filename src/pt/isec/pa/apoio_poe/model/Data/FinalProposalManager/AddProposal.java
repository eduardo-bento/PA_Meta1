package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;

public class AddProposal extends CommandAdapter {
    String proposalID;
    long studentID;

    public AddProposal(FinalProposalManager receiver,String proposalID,long studentID) {
        super(receiver);
        this.proposalID = proposalID;
        this.studentID = studentID;
    }

    @Override
    public boolean execute() {
        return receiver.manualAttribution_(proposalID,studentID);
    }

    public boolean undo() {
        return receiver.manuelRemove(proposalID);
    }
}
