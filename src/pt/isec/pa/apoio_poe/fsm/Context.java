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

    public boolean insert(Object object) {
        return state.insert(object);
    }

    public <T,K> boolean edit(T entity,K value,String label) {
        return state.edit(entity,value,label);
    }

    public <T> boolean remove(T id) {
        return state.remove(id);
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
