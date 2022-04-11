package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.data.Flyweight;

public class ConfigurationState extends ContextAdapter {
    private EManagement management;
    public ConfigurationState(Context context, Data data) {
        super(context, data);
        management = EManagement.STUDENTS;
    }

    @Override
    public void changeManagementMode(EManagement management) {
        this.management = management;
    }

    @Override
    public <T> boolean insert(Object object,Class<T> typeClass) {
       return data.insert(object,typeClass);
    }

    @Override
    public <T,K,A> boolean edit(T entity, K value, String label, Class<A> typeClass) {
        data.edit(entity,value,label,typeClass);
        return true;
    }

    @Override
    public boolean closePhase() {
        if (Flyweight.branchGreatherProposals()){
            return data.lockPhase(getState());
        }
        return false;
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
    public void goCandidacy() {
        changeState(EState.CANDIDACY);
    }

    @Override
    public EState getState() {
        return EState.CONFIGURATION;
    }
}
