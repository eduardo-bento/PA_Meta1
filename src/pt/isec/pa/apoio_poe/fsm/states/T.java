package pt.isec.pa.apoio_poe.fsm.states;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class T extends ContextAdapter {
    public T(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void attributeATeacher(String proposalID, String teacherID) {
        data.attributeATeacher(proposalID,teacherID);
    }

    @Override
    public String querying() {
        return super.querying();
    }

    @Override
    public EState getState() {
        return null;
    }
}
