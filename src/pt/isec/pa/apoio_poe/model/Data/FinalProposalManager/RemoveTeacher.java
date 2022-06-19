package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.Log;

public class RemoveTeacher extends CommandAdapter{
    private String proposalID;
    private String teacherID;

    public RemoveTeacher(FinalProposalManager receiver,String proposalID) {
        super(receiver);
        this.proposalID = proposalID;
    }

    @Override
    public boolean execute() {
        teacherID = receiver.getProposal(proposalID).getTeacher();
        return receiver.manualTeacherRemove_(proposalID);
    }

    public boolean undo() {
        return receiver.manualTeacherAttribution_(proposalID,teacherID);
    }
}
