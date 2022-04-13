package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;

import java.util.List;

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

    public EManagement getManagementMode(){
        return state.getManagementMode();
    }
    public boolean closePhase(){
        return state.closePhase();
    }

    public void forward() {
        state.forward();
    }

    public boolean back() {
        return state.back();
    }

    public String getListOfStudents() {
        return state.getListOfStudents();
    }

    public String getFilterList(List<Integer> filters){
        return state.getFilterList(filters);
    }

    public EState getState(){
        return state.getState();
    }
}
