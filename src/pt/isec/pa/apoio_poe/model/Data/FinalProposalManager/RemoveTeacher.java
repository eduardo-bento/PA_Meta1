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
        String teacherID = receiver.manualTeacherRemove(proposalID);
        if(teacherID.isEmpty()){
            return false;
        }
        this.teacherID = teacherID;
        return true;
    }

    public boolean undo() {
        return receiver.manualTeacherAttribution(proposalID,teacherID);
    }
}
