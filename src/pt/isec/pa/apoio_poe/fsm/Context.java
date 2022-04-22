package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

import java.util.List;

public class Context {
    IState state;
    Data data;

    public Context() {
        data = new Data();
        state = pt.isec.pa.apoio_poe.fsm.EState.CONFIGURATION.stateFactory(this,data);
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

    public <T> boolean remove(T id) {
        return state.remove(id);
    }

    public String querying(){
        return state.querying();
    }

    public void changeMode(EState management) {
        state.changeMode(management);
    }

    public EState getMode(){
        return state.getMode();
    }

    public boolean closePhase(){
        return state.closePhase();
    }

    public boolean isPhaseLock(){
        return state.isPhaseLock();
    }

    public void forward() {
        state.forward();
    }

    public boolean back() {
        return state.back();
    }

    public void readFromFile(String filePath){
        state.readFromFile(filePath);
    }

    public void manualProposalAttribution(String proposalID,long studentID){
        state.manualProposalAttribution(proposalID,studentID);
    }

    public void manualProposalRemoveAttribution(String proposalID){

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

    public void goToMode() {
        state.goToMode();
    }
}
