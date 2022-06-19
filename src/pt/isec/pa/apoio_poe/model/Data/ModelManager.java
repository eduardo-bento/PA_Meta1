package pt.isec.pa.apoio_poe.model.Data;

import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.fsm.IState;
import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.FX.TopMenu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ModelManager {
    public static final String PROP_STATE = "state";
    public static final String PROP_DATA  = "data";
    public static final String PROP_UPDATE  = "update";

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

    public boolean undo(){
        boolean value = context.undo();
        pcs.firePropertyChange(PROP_DATA,null,null);
        return value;
    }

    public int getNumberDestiny(String type){
        return context.getNumberDestiny(type);
    }

    public List<Integer> getPercentage(){
        return context.getPercentage();
    }

    public boolean redo(){
        boolean value = context.redo();
        pcs.firePropertyChange(PROP_DATA,null,null);
        return value;
    }

    public List<Teacher> top5(){
        return context.top5();
    }
    public <T,K,A> boolean edit(T entity,K value,String label,Class<A> typeClass) {
        boolean v = context.edit(entity,value,label,typeClass);
        pcs.firePropertyChange(PROP_DATA,null,null);
        return v;
    }

    public <T> boolean remove(T id) {
        boolean value =  context.remove(id);
        pcs.firePropertyChange(PROP_DATA,null,null);
        return value;
    }

    public List<Object> querying(){
        return context.querying();
    }

    public boolean closePhase(){
        boolean value = context.closePhase();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
        return value;
    }

    public void forward() {
        context.forward();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public boolean back() {
        boolean value = context.back();
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
        pcs.firePropertyChange(PROP_UPDATE,null,null);
        return value;
    }

    public List<Student> getStudents(){
        return context.getStudents();
    }

    public List<Teacher> getTeachers(){
        return context.getTeachers();
    }

    public List<Student> getStudentsListWithoutCandidacy() {
        return context.getStudentsListWithoutCandidacy();
    }

    public List<Student> getStudentsListWithCandidacy() {
        return context.getStudentsListWithCandidacy();
    }

    public List<Student> getStudentsListNoProposal() {
        return context.getStudentsListNoProposal();
    }

    public List<Proposal> getProjectsList() {
        return context.getProjectsList();
    }

    public List<Proposal> getProposalsWithCandidacyList() {
        return context.getProposalsWithCandidacyList();
    }

    public List<Proposal> getProposalsWithoutCandidacyList() {
        return context.getProposalsWithoutCandidacyList();
    }

    public List<Student> getStudentsListWithSelfProposal() {
        return context.getStudentsListWithSelfProposal();
    }

    public List<Proposal> getSelfProposalList() {
        return context.getSelfProposalList();
    }

    public List<Proposal> getProposalsAvailableList() {
        return context.getProposalsAvailableList();
    }

    public List<Proposal> getProposalsAttributedList() {
        return context.getProposalsAttributedList();
    }


    public boolean readFromFile(String filePath){
        boolean value = context.readFromFile(filePath);
        pcs.firePropertyChange(PROP_DATA,null,null);
        return value;
    }

    public void automaticAttribution(){
        context.automaticAttribution();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void automaticAssignmentForProjectAndInterShip(){
        context.automaticAssignmentForProjectAndInterShip();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void manualTeacherAttribution(String proposalID, String teacherID){
        context.manualTeacherAttribution(proposalID,teacherID);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public String getAttributionTeacherData(){
        return context.getAttributionTeacherData();
    }

    public List<FinalProposal> getFinalProposalWithoutTeacherList() {
        return context.getFinalProposalWithoutTeacherList();
    }

    public List<FinalProposal> getFinalProposalWithTeacherList() {
        return context.getFinalProposalWithTeacherList();
    }


    public float getTeachersAverage() {
        return context.getTeachersAverage();
    }

    public int getTeachersHighest() {
        return context.getTeachersHighest();
    }

    public int getTeachersLowest() {
        return context.getTeachersLowest();
    }

    public void automaticTeacherAttribution(){
        context.automaticTeacherAttribution();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public String getData(){
        return context.getData();
    }

    public List<FinalProposal> getFinalProposalsList() {
        return context.getFinalProposalsList();
    }

    public List<Student> getStudentsWithAssignedProposalList() {
        return context.getStudentsWithAssignedProposalList();
    }

    public List<Student> getStudentsWithoutFinalProposalAndWithCandidacyList() {
        return context.getStudentsWithoutFinalProposalAndWithCandidacyList();
    }

    public void manualRemove(String proposalID){
        context.manualRemove(proposalID);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public String getListOfStudents() {
        return context.getListOfStudents();
    }

    public String getFilterList(List<Integer> filters){
        String value = context.getFilterList(filters);
        return value;
    }

    public EState getState(){
        return context.getState();
    }

    public void goToMode(int option) {
        context.goToMode(option);
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
        pcs.firePropertyChange(PROP_UPDATE,null,null);
    }

    public void exportFile(String filePath) {
        context.exportFile(filePath);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public boolean manualTeacherRemove(String proposalID){
        return context.manualTeacherRemove(proposalID);
    }

    public void save() {
        context.save();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void load() {
        context.load();
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void manualAttribution(String proposal_id, long student_id) {
        context.manualAttribution(proposal_id,student_id);
        pcs.firePropertyChange(PROP_DATA,null,null);
    }

    public void handleConflict(long studentId, String proposalId) {
        context.handleConflict(studentId,proposalId);
        pcs.firePropertyChange(PROP_STATE,null,context.getState());
    }

    public String getTeacherList() {
        return context.getTeacherList();
    }
}
