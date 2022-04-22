package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.dataStrucutures.EDataStructure;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;

public class ConfigurationState extends ContextAdapter {
    public ConfigurationState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void changeMode(EState management) {
        data.setCurrentMode(management);
    }

    @Override
    public EState getMode() {
        return data.getCurrentMode();
    }

    @Override
    public void goToMode() {
        changeState(data.getCurrentMode());
    }

    @Override
    public boolean isPhaseLock() {
        return data.isPhaseLock(EState.CONFIGURATION);
    }

    @Override
    public boolean closePhase() {
        if (data.lockConfigurationPhase()){
            Log.getInstance().addMessage("Configuration Phase locked");
            return data.lockPhase(getState());
        }
        return false;
    }

    @Override
    public String querying() {
        return null;
    }

    @Override
    public void forward() {
        changeState(EState.CANDIDACY);
    }

    @Override
    public EState getState() {
        return EState.CONFIGURATION;
    }
}
