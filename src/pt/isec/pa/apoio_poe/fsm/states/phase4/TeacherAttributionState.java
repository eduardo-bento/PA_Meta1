package pt.isec.pa.apoio_poe.fsm.states.phase4;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Teacher.Teacher;

public class TeacherAttributionState extends ContextAdapter {
    public TeacherAttributionState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public String getAttributionTeacherData() {
        return data.getAttributionTeacherData();
    }

    @Override
    public void exportFile(String filePath) {
        data.exportPhase4(filePath);
    }

    @Override
    public boolean manualTeacherRemove(String proposalID) {
       return data.manualTeacherRemove(proposalID);
    }

    @Override
    public void manualTeacherAttribution(String proposalID, String teacherID) {
        data.manualTeacherAttribution(proposalID,teacherID);
    }

    @Override
    public boolean closePhase() {
        data.lockPhase(3);
        changeState(EState.QUERYING_PHASE);
        return true;
    }

    @Override
    public boolean back() {
        if (!data.isPhaseLock(2)){
            changeState(EState.PROPOSALS_PHASE);
            return true;
        }
        changeState(EState.PROPOSALS_PHASE_LOCK);
        return false;
    }

    @Override
    public String querying() {
        return data.teacherQuerying();
    }

    @Override
    public void automaticTeacherAttribution() {
        data.automaticTeacherAttribution();
    }

    @Override
    public String getTeacherList() {
        return data.getTeacherList();
    }

    @Override
    public EState getState() {
        return EState.TEACHER_ATTRIBUTION_PHASE;
    }
}
