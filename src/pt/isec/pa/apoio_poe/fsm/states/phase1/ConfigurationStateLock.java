package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class ConfigurationStateLock extends ContextAdapter {
    public ConfigurationStateLock(Context context, Data data) {
        super(context, data);
    }

    @Override
    public EState getMode() {
        return data.getCurrentMode();
    }

    @Override
    public void changeMode(EState management) {
        data.setCurrentMode(management);
    }

    @Override
    public String querying() {
        return data.querying(data.getCurrentMode().getClass());
    }

    @Override
    public void forward() {
        changeState(EState.CANDIDACY);
    }

    @Override
    public EState getState() {
        return EState.CONFIGURATION_PHASE_LOCK;
    }
}
