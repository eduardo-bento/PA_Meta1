package pt.isec.pa.apoio_poe.fsm.states.phase4;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

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
        return false;
    }

    @Override
    public void forward() {
        changeState(EState.QUERYING_PHASE);
    }

    @Override
    public void automaticTeacherAttribution() {
        data.automaticTeacherAttribution();
    }

    @Override
    public EState getState() {
        return EState.TEACHER_ATTRIBUTION_PHASE;
    }
}
