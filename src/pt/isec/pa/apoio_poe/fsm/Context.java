package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

import java.io.*;
import java.util.List;

public class Context {
    static final String FILENAME = "data.data";
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

    public List<Object> querying(){
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

    public List<Student> getStudents() {
       return state.getStudents();
    }

    public List<Teacher> getTeachers() {
        return state.getTeachers();
    }

    public List<Student> getStudentsListNoProposal() {
        return state.getStudentsListNoProposal();
    }

    public List<Student> getStudentsListWithCandidacy() {
        return state.getStudentsListWithCandidacy();
    }

    public List<Student> getStudentsListWithoutCandidacy() {
        return state.getStudentsListWithoutCandidacy();
    }

    public List<Proposal> getProjectsList() {
        return state.getProjectsList();
    }

    public List<Proposal> getProposalsWithCandidacyList() {
        return state.getProposalsWithCandidacyList();
    }

    public List<Proposal> getProposalsWithoutCandidacyList() {
        return state.getProposalsWithoutCandidacyList();
    }

    public List<Proposal> getProposalsAttributedList() {
        return state.getProposalsAttributedList();
    }

    public List<Proposal> getProposalsAvailableList() {
        return state.getProposalsAvailableList();
    }

    public List<Proposal> getSelfProposalList() {
        return state.getSelfProposalList();
    }

    public List<Student> getStudentsListWithSelfProposal() {
        return state.getStudentsListWithSelfProposal();
    }

    public boolean readFromFile(String filePath){
        return state.readFromFile(filePath);
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

    public List<FinalProposal> getFinalProposalWithoutTeacherList() {
        return state.getFinalProposalWithoutTeacherList();
    }

    public float getTeachersAverage() {
        return state.getTeachersAverage();
    }

    public int getTeachersHighest() {
        return state.getTeachersHighest();
    }

    public int getTeachersLowest() {
        return state.getTeachersLowest();
    }

    public List<FinalProposal> getFinalProposalWithTeacherList() {
        return state.getFinalProposalWithTeacherList();
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

    public List<FinalProposal> getFinalProposalsList() {
        return state.getFinalProposalsList();
    }

    public List<Student> getStudentsWithAssignedProposalList() {
        return state.getStudentsWithAssignedProposalList();
    }

    public List<Student> getStudentsWithoutFinalProposalAndWithCandidacyList() {
        return state.getStudentsWithoutFinalProposalAndWithCandidacyList();
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

    public boolean undo() {
        return state.undo();
    }

    public int getNumberDestiny(String type){
        return state.getNumberDestiny(type);
    }

    public List<Integer> getPercentage(){
        return state.getPercentage();
    }

    public List<Teacher> top5(){
        return state.top5();
    }
    public boolean redo(){
        return state.redo();
    }

}
