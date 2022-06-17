package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.Data.TieBreaker;
import pt.isec.pa.apoio_poe.model.Log;

public class AddTeacher extends CommandAdapter {
    String proposalID,teacherID;

    public AddTeacher(FinalProposalManager receiver, String proposalID, String teacherID) {
        super(receiver);
        this.proposalID = proposalID;
        this.teacherID = teacherID;
    }

    @Override
    public boolean execute() {
        Teacher teacher = receiver.find(teacherID,Teacher.class);
        if (receiver.find(teacherID,Teacher.class) != null){
            FinalProposal proposal = receiver.getProposal(proposalID);
            if (proposal != null){
                proposal.setTeacher(teacherID);
                teacher.addToAmount();
                Log.getInstance().addMessage("The teacher with email " + teacher.getEmail() +
                        " was added to the proposal " + proposalID);
                return true;
            }
        }
        return false;
    }

    public boolean undo() {
        receiver.manualTeacherRemove(proposalID);
        return true;
    }
}
