package pt.isec.pa.apoio_poe.fsm.states.phase4;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class TieBreakerPhase extends ContextAdapter {
    public TieBreakerPhase(Context context, Data data) {
        super(context, data);
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