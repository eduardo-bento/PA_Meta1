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
        return receiver.manualTeacherAttribution_(proposalID,teacherID);
    }

    public boolean undo() {
        return !receiver.manualTeacherRemove_(proposalID);
    }
}
