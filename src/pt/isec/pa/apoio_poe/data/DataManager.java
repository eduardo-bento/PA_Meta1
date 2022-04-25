package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.Memento.CareTaker;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.model.FinalProposal;
import pt.isec.pa.apoio_poe.model.Manager.*;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/*public class DataManager {
    private final Data data;
    private final CareTaker careTaker;

    public DataManager() {
        data = new Data();
        careTaker = new CareTaker(data);
    }

    public EState getCurrentMode() {
        return data.getCurrentMode();
    }

    public void setCurrentMode(EState currentMode) {
        data.setCurrentMode(currentMode);
    }

    public boolean isPhaseLock(int type){
        return phasesLock[type];
    }

    public boolean lockPhase(int type){
        return phasesLock[type] = true;
    }

    public boolean insert(Object item){
        return data.insert(item);
    }

    public <Q, K, A> boolean edit(Q id, K value, String label, Class<A> type) {
        return data.edit(id,value,label,type);
    }

    public <T> boolean remove(T id, Class<?> type){
        return data.remove(id,type);
    }

    public String querying(Class<?> type) {
        return data.querying(type);
    }

    public String getListOfStudents_CandidacyPhase(){
        return data.getListOfStudents_CandidacyPhase();
    }

    public String getListProposals_CandidacyPhase(List<Integer> filters){
        return data.getListProposals_CandidacyPhase(filters);
    }

    public void readCSV(String filePath, Class<?> type){
        data.readCSV(filePath,type);
    }

    public boolean lockConfigurationPhase(){
        return data.lockConfigurationPhase();
    }

    public void manualProposalAttribution(String proposalID,long studentID){
        data.manualProposalAttribution(proposalID,studentID);
    }

    public void manualProposalRemoveAttribution(String proposalID) {
        data.manualProposalRemoveAttribution(proposalID);
    }

    public void automaticAttribution(){
        data.automaticAttribution();
    }

    public void automaticAttributionForProposalsWithStudent() {
        data.automaticAttributionForProposalsWithStudent();
    }

    public String getListOfStudentsFinal(){
        return data.getListOfStudentsFinal();
    }

    public String getListProposalsFinal(List<Integer> filters){
        return data.getListProposalsFinal(filters);
    }

    public boolean lockProposalPhase(){
        return data.lockProposalPhase();
    }

    public String getAttributionTeacherData() {
        return data.getAttributionTeacherData();
    }

    public void automaticTeacherAttribution() {
        data.automaticTeacherAttribution();
    }

    public String getData() {
        return data.getData();
    }

    public void attributeATeacher(String proposalID, String teacherID) {
        data.attributeATeacher(proposalID,teacherID);
    }

    public void undo(){
        careTaker.undo();
    }

    public void redo(){
        careTaker.redo();
    }

}*/
