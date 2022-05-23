package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

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

    public void changeState(IState state){
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

    public void automaticAssignmentForProjectAndInterShip(){
        state.automaticAssignmentForProjectAndInterShip();
    }

    public void manualTeacherAttribution(String proposalID, String teacherID){
        state.manualTeacherAttribution(proposalID,teacherID);
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

    public void goToMode(int option) {
        state.goToMode(option);
    }

    public void exportFile(String filePath) {
        state.exportFile(filePath);
    }

    public boolean manualTeacherRemove(String proposalID){
       return state.manualTeacherRemove(proposalID);
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

    public void manualAttribution(String proposal_id, long student_id) {
        state.manualAttribution(proposal_id,student_id);
    }

    public void handleConflict(long studentId, String proposalId) {
        state.handleConflict(studentId,proposalId);
    }

    public String getTeacherList() {
        return state.getTeacherList();
    }
}
