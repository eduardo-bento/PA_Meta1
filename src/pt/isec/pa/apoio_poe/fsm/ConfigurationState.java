package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;

public class ConfigurationState extends ContextAdapter {
    public ConfigurationState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public void changeManagementMode(EManagement management) {
        data.setCurrentMode(management);
    }

    @Override
    public EManagement getManagementMode() {
        return data.getCurrentMode();
    }

    @Override
    public boolean insert(Object object) {
       return data.insert(object);
    }

    @Override
    public <T,K,A> boolean edit(T entity, K value, String label, Class<A> typeClass) {
        data.edit(entity,value,label,typeClass);
        return true;
    }
    @Override
    public boolean closePhase() {
        return data.lockPhase(getState());
    }

    @Override
    public <T,K> boolean remove(T entity,Class<K> typeClass) {
        data.remove(entity,typeClass);
        return true;
    }

    @Override
    public <T> String querying(Class<T> typeClass) {
        return data.querying(typeClass);
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
