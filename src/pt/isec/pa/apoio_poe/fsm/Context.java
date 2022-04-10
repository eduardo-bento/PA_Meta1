package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;

public class Context {
    IState state;
    Data data;

    public Context() {
        data = new Data();
        state = EState.CONFIGURATION.factory(this,data);
    }

    void changeState(IState state){
        this.state = state;
    }

    public <T> boolean insert(Object object,Class<T> typeClass) {
        return state.insert(object,typeClass);
    }

    public <T,K,A> boolean edit(T entity,K value,String label,Class<A> typeClass) {
        return state.edit(entity,value,label,typeClass);
    }

    public <T,K> boolean remove(T id,Class<K> typeClass) {
        return state.remove(id,typeClass);
    }

    public <T> String querying(Class<T> typeClass){
        return state.querying(typeClass);
    }

    public void changeManagementMode(EManagement management) {
        state.changeManagementMode(management);
    }

    public void goCandidacy() {
        state.goCandidacy();
    }

    public boolean backConfiguration() {
        return state.backConfiguration();
    }

    public String getListOfStudents() {
        return state.getListOfStudents();
    }

    public String getListOfProjects_Stage() {
        return state.getListOfProjects_Stages();
    }

    public EState getState(){
        return state.getState();
    }
}
