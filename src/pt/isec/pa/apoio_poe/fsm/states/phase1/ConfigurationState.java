package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Student;

import java.io.Serializable;

public class ConfigurationState extends ContextAdapter implements Serializable {
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
    public boolean closePhase() {
        if (data.lockConfigurationPhase()){
            Log.getInstance().addMessage("Configuration Phase locked");
            data.lockPhase(0);
            changeState(EState.CONFIGURATION_PHASE_LOCK);
            return true;
        }
        return false;
    }

    @Override
    public String querying() {
        return data.querying(Student.class);
    }

    @Override
    public void readFromFile(String filePath) {
        data.readCSV(filePath,data.getCurrentMode().getStructureClass());
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
