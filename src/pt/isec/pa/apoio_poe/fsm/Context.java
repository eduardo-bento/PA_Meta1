package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Student;

import java.awt.*;
import java.io.*;
import java.util.List;

public class Context {
    static final String FILENAME = "d.data";
    private IState state;
    private Data data;

    public Context() {
        data = new Data();
        state = EState.CONFIGURATION_PHASE.stateFactory(this,data);
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

    public void forward() {
        state.forward();
    }

    public boolean back() {
        return state.back();
    }

    public void readFromFile(String filePath){
        state.readFromFile(filePath);
    }

    public void automaticAttribution(){
        state.automaticAttributionWithoutAssociation();
    }

    public void automaticAttributionForProposalsWithStudent(){
        state.automaticAttributionForProposalsWithStudent();
    }

    public void manualAttribution(String proposalID,long studentID){
        state.manualAttribution(proposalID,studentID);
    }

    public String getAttributionTeacherData(){
        return state.getAttributionTeacherData();
    }

    public void automaticTeacherAttribution(){
        state.automaticTeacherAttribution();
    }

    public String getData(){
        return state.getData();
    }

    public void manualRemove(String proposalID){
        state.manualRemove(proposalID);
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

    public void save() {
        try(ObjectOutputStream object = new ObjectOutputStream(
                new FileOutputStream(FILENAME))) {
            object.writeObject(data);
            object.writeObject(state.getState());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void load() {
        try(ObjectInputStream object = new ObjectInputStream(
                new FileInputStream(FILENAME))) {
            data = (Data) object.readObject();
            EState state = (EState) object.readObject();
            changeState(state.stateFactory(this,data));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
