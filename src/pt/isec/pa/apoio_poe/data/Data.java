package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.FinalProposal;
import pt.isec.pa.apoio_poe.model.Manager.*;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })

public class Data implements Serializable {
    private EState currentMode;
    private final boolean[] phasesLock;
    private final Map<Class<?>, Manager> management;
    public Data() {
        currentMode = EState.STUDENT;
        management = Map.of(
                Student.class,new StudentManager(this),
                Teacher.class,new TeacherManager(this),
                Proposal.class,new ProposalManager(this),
                Candidacy.class,new CandidacyManager(this),
                FinalProposal.class,new FinalProposalManager(this)
        );
        phasesLock = new boolean[5];
    }

    public Map<Class<?>, Manager> getManagement() {
        return management;
    }

    public <K> Set<K> getList (Class<K> type){
        return management.get(type).getList();
    }

    public Set<Proposal> getSelfProposalSet(){
        return ((ProposalManager)management.get(Proposal.class)).getSpecific(SelfProposal.class);
    }

    public EState getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(EState currentMode) {
        this.currentMode = currentMode;
    }

    public boolean isPhaseLock(int type){
        return phasesLock[type];
    }

    public boolean lockPhase(int type){
        return phasesLock[type] = true;
    }

    public boolean insert(Object item){
        if (item == null) return false;
        Class<?> type = item.getClass();

        Manager manager = management.get(Utils.getSuperClass(item.getClass()));
        if (manager.insert(item)){
            Log.getInstance().addMessage(Utils.splitString(type.getName(), "\\.") + " added");
            return true;
        }
        Log.getInstance().addMessage(Utils.splitString(type.getName(), "\\.") + " was not added");
        return false;
    }

    public <Q, K, A> boolean edit(Q id, K value, String label, Class<A> type) {
        Manager manager = management.get(type);
        return manager.edit(id,value,label,type);
    }

    public <T> boolean remove(T id, Class<?> type){
        Manager manager = management.get(type);

        if (manager.remove(id)) {
            Log.getInstance().addMessage("The " + Utils.splitString(type.getName(), "\\.") + " was successfully removed");
            return true;
        }
        Log.getInstance().addMessage("The " + Utils.splitString(type.getName(), "\\.") + " was not removed");
        return false;
    }

    public String querying(Class<?> type) {
        Manager manager = management.get(Utils.getSuperClass(type));
        return manager.querying();
    }

    public String getListOfStudents_CandidacyPhase(){
        StudentManager manager = (StudentManager) management.get(Student.class);
        return "With candidacy" + "\n" + manager.getStudentsCandidacy(true) + "\n" +
                "Without candidacy" + "\n" + manager.getStudentsCandidacy(false) +
                "Self Proposal" + "\n" + manager.getStudentWithSelfProposal();
    }

    public String getListProposals_CandidacyPhase(List<Integer> filters){
        ProposalManager manager = (ProposalManager) management.get(Proposal.class);
        StringBuilder stringBuilder = new StringBuilder();
        for (int f : filters){
            switch (f){
                case 1 -> stringBuilder.append("SelfProposals").append("\n").append(manager.getSelfProposalList());
                case 2 -> stringBuilder.append("Teacher Proposals").append("\n").append(manager.getProjectList());
                case 3 -> stringBuilder.append("Proposals with candidacy").append("\n").append(manager.getProposalsWithCandidacy());
                case 4 -> stringBuilder.append("Proposals without candidacy").append("\n").append(manager.getProposalsWithoutCandidacy());
                }
            }
        return stringBuilder.toString();
    }

    public void readCSV(String filePath, Class<?> type){
        try {
            List<Object> objects = (List<Object>) Utils.getSuperClass(type).getMethod("readFile",String.class).invoke(null,filePath);
            objects.forEach(this::insert);
        } catch (NoSuchMethodException e ) {
            System.err.println("No method found" + type.getName());
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.err.println("Could not invoke the method");
        }
    }

    public boolean lockConfigurationPhase(){
        StudentManager student = (StudentManager) management.get(Student.class);
        ProposalManager proposal = (ProposalManager) management.get(Proposal.class);
        return student.branchCount("RAS") >= proposal.branchCount("RAS") &&
                student.branchCount("SI") >= proposal.branchCount("SI") &&
                student.branchCount("DA") >= proposal.branchCount("DA");
    }

    public void manualProposalAttribution(String proposalID,long studentID){
        FinalProposalManager proposal = (FinalProposalManager) management.get(FinalProposal.class);
        if(!proposal.manualAttribution(proposalID,studentID)){
            Log.getInstance().addMessage("The proposal id or the student does not exit");
        }
    }

    public void manualProposalRemoveAttribution(String proposalID) {
        FinalProposalManager proposals = (FinalProposalManager) management.get(FinalProposal.class);
        if(!proposals.manuelRemove(proposalID)){
            Log.getInstance().addMessage("The proposal id or the student does not exit");
        }
    }

    public void automaticAttribution(){
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        manager.automaticAttribution();
    }

    public void automaticAttributionForProposalsWithStudent() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        manager.automaticProposals();
    }

    public String getListOfStudentsFinal(){
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.getListOfStudents();
    }

    public String getListProposalsFinal(List<Integer> filters){
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.getListOfProposals(filters);
    }

    public boolean lockProposalPhase(){
        int count = 0;
        List<Candidacy> candidacies = new ArrayList<>(management.get(Candidacy.class).getList());
        List<FinalProposal> proposals = new ArrayList<>(management.get(FinalProposal.class).getList());

        for (Candidacy candidacy : candidacies){
            if(proposals.contains(FinalProposal.getFakeFinalproposal(candidacy.getStudentId())))
                count++;
        }

        return candidacies.size() == count;
    }

    public String getAttributionTeacherData() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        TeacherManager teacherManager = (TeacherManager) management.get(Teacher.class);
        return "List of students with teacher associated\n" + manager.getProposalTeacher(true) +
                "List of students without teacher associated\n" + manager.getProposalTeacher(false) +
                "Average: " + teacherManager.average() + " Max: " + teacherManager.highest() + " Min: " + teacherManager.lowest();
    }

    public void automaticTeacherAttribution() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        manager.automaticTeacherAttribution();
    }

    public String getData() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        TeacherManager teacherManager = (TeacherManager) management.get(Teacher.class);
        return "List of students with attributed proposal\n" + manager.listOfStudentsWithFinalProposal() +
                "List of students without attributed proposal but with candidacy options\n" + manager.listOfStudentsWithoutFinalProposalAndWithCandidacy() +
                "Available Proposals\n" + manager.listOfAvailableProposals() +
                "Proposals Attributed\n" + manager.listOfFinalProposals() +
                "Average: " + teacherManager.average() + " Max: " + teacherManager.highest() + " Min: " + teacherManager.lowest();
    }

    public void attributeATeacher(String proposalID, String teacherID) {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        manager.attributeATeacher(proposalID,teacherID);
    }
}