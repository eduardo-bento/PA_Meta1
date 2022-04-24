package pt.isec.pa.apoio_poe.fsm.states;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class Querying extends ContextAdapter {
    public Querying(Context context, Data data) {
        super(context, data);
    }

    @Override
    public EState getState() {
        return null;
    }
}
