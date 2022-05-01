package pt.isec.pa.apoio_poe.fsm.states.phase5;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class Querying extends ContextAdapter {
    public Querying(Context context, Data data) {
        super(context, data);
    }

    @Override
    public String getData() {
        return data.getData();
    }

    @Override
    public void exportFile(String filePath) {
        data.exportPhase4(filePath);
    }

    @Override
    public EState getState() {
        return EState.QUERYING_PHASE;
    }
}
