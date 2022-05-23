package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.model.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

public class ConfigurationState extends ContextAdapter {
    public ConfigurationState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void goToMode(int option) {
        switch (option){
            case 1 -> changeState(EState.STUDENT);
            case 2 -> changeState(EState.TEACHER);
            case 3 -> changeState(EState.PROPOSAL);
        }
    }

    @Override
    public boolean closePhase() {
        if (data.lockConfigurationPhase()){
            Log.getInstance().addMessage("Configuration Phase locked");
            data.lockPhase(0);
            changeState(EState.CONFIGURATION_PHASE_LOCK);
            return true;
        }
        Log.getInstance().addMessage("Configuration Phase was not locked");
        return false;
    }

    @Override
    public void forward() {
        changeState(EState.CANDIDACY);
    }

    @Override
    public EState getState() {
        return EState.CONFIGURATION_PHASE;
    }
}
