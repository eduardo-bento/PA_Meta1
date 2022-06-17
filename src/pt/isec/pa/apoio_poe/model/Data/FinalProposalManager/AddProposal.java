package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;

public class AddProposal extends CommandAdapter {
    FinalProposal proposal;
    String proposalID;
    long studentID;
    public AddProposal(FinalProposalManager receiver,String proposalID,long studentID) {
        super(receiver);
        this.proposalID = proposalID;
        this.studentID = studentID;
    }

    @Override
    public boolean execute() {
        Student student = receiver.find(studentID,Student.class);
        Proposal proposal = receiver.find(proposalID,Proposal.class);
        if (student == null || proposal == null) return false;

        if (proposal.getStudent() != -1) return false;

        FinalProposal finalProposal = receiver.find(studentID,FinalProposal.class);
        if (finalProposal == null){
            proposal.setStudent(studentID);
            receiver.insert(new FinalProposal(studentID,proposalID,-1));
        }
        proposal.setAssigned(true);
        receiver.linkToStudent(studentID);

        this.proposal = new FinalProposal(studentID,proposalID,-1);
        return true;
    }

    public boolean undo() {
        receiver.manuelRemove(proposal.getProposal());
        return true;
    }
}
