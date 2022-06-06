package pt.isec.pa.apoio_poe.model.Data;

import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.fsm.IState;
import pt.isec.pa.apoio_poe.model.FX.TopMenu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ModelManager {
    public static final String PROP_STATE = "state";
    public static final String PROP_DATA  = "data";

    TopMenu topMenu;
    Context context;
    PropertyChangeSupport pcs;

    public ModelManager() {
        topMenu = new TopMenu(this);
        context = new Context();
        pcs = new PropertyChangeSupport(this);
    }

    public TopMenu getTopMenu(){
        return topMenu;
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(property, listener);
    }

    void changeState(IState state){
        context.changeState(state);
    }

    public boolean insert(Object object) {
        boolean value = context.insert(object);
        pcs.firePropertyChange(PROP_DATA,null,null);
        return value;
    }

    public <T,K,A> boolean edit(T entity,K value,String label,Class<A> typeClass) {
        return context.edit(entity,value,label,typeClass);
    }

    public <T> boolean remove(T id) {
        boolean value =  context.remove(id);
        pcs.firePropertyChange(PROP_DATA,null,null);
        return value;
    }

    public String querying(){
        return context.querying();
    }

    public boolean closePhase(){
        if (context.closePhase()){
            pcs.firePropertyChange(PROP_STATE,null,context.getState());
            return true;
        }
        return false;
    }

    public void forward() {
        context.forward();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public boolean back() {
        context.back();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
        return true;
    }

    public void readFromFile(String filePath){
        context.readFromFile(filePath);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void automaticAttribution(){
        context.automaticAttribution();
    }

    public void automaticAssignmentForProjectAndInterShip(){
        context.automaticAssignmentForProjectAndInterShip();
    }

    public void manualTeacherAttribution(String proposalID, String teacherID){
        context.manualTeacherAttribution(proposalID,teacherID);
    }

    public String getAttributionTeacherData(){
        return context.getAttributionTeacherData();
    }

    public void automaticTeacherAttribution(){
        context.automaticTeacherAttribution();
    }

    public String getData(){
        return context.getData();
    }

    public void manualRemove(String proposalID){
        context.manualRemove(proposalID);
    }

    public String getListOfStudents() {
        return context.getListOfStudents();
    }

    public String getFilterList(List<Integer> filters){
        return context.getFilterList(filters);
    }

    public EState getState(){
        return context.getState();
    }

    public void goToMode(int option) {
        context.goToMode(option);
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public void exportFile(String filePath) {
        context.exportFile(filePath);
    }

    public boolean manualTeacherRemove(String proposalID){
        return context.manualTeacherRemove(proposalID);
    }

    public void save() {
        context.save();
    }

    public void load() {
        context.load();
    }

    public void manualAttribution(String proposal_id, long student_id) {
        context.manualAttribution(proposal_id,student_id);
    }

    public void handleConflict(long studentId, String proposalId) {
        context.handleConflict(studentId,proposalId);
    }

    public String getTeacherList() {
        return context.getTeacherList();
    }
}
