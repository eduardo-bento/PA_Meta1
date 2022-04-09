package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

public class CandicyState extends ContextAdapter{
    public CandicyState(Context context, Data data) {
        super(context, data);
    }


    @Override
    public boolean backConfiguration() {
        changeState(EState.CONFIGURATION);
        return true;
    }

    @Override
    public EState getState() {
        return EState.CANDIDACY;
    }
}
