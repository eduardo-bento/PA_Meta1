package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.model.*;
import pt.isec.pa.apoio_poe.model.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student.Student;
import pt.isec.pa.apoio_poe.model.Teacher.Teacher;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.io.*;
import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })

public class Data implements Serializable {
    private final boolean[] phasesLock;
    private final Map<Class<?>, Manager> management;
    public Data() {
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

    public <K> List<K> getList (Class<K> type){
        return new ArrayList<>(management.get(type).getList());
    }

    public List<Proposal> getSelfProposalList(){
        return new ArrayList<>(((ProposalManager)management.get(Proposal.class)).getSpecific(SelfProposal.class));
    }

    public List<Student> getListOfStudentsWithNoProposal(){
        StudentManager studentManager = (StudentManager) management.get(Student.class);
        return studentManager.getListOfStudentsWithNoProposals();
    }

    public List<Proposal> getProposalsNotAssigned(){
        ProposalManager proposalManager = (ProposalManager) management.get(Proposal.class);
        return proposalManager.getProposalsNotAssigned();
    }

    public boolean isPhaseLock(int type){
        return phasesLock[type];
    }

    public void lockPhase(int type){
        phasesLock[type] = true;
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
        return manager.querying().isEmpty() ? "no data found" : manager.querying();
    }

    public String getListOfStudents_CandidacyPhase(){
        StudentManager manager = (StudentManager) management.get(Student.class);
        return "With candidacy\n" + manager.getStudentsWithCandidacy()  +
                "Without candidacy\n" + manager.getStudentsWithoutCandidacy() +
                "Self Proposal\n" + manager.getStudentsWithSelfProposal();
    }

    public String getListProposals_CandidacyPhase(List<Integer> filters){
        ProposalManager manager = (ProposalManager) management.get(Proposal.class);
        StringBuilder stringBuilder = new StringBuilder();
        for (int f : filters){
            switch (f){
                case 1 -> stringBuilder.append("SelfProposals\n").append(manager.getSelfProposalList());
                case 2 -> stringBuilder.append("Teacher Proposals\n").append(manager.getProjectList());
                case 3 -> stringBuilder.append("Proposals with candidacy\n").append(manager.getProposalsWithCandidacy());
                case 4 -> stringBuilder.append("Proposals without candidacy\n").append(manager.getProposalsWithoutCandidacy());
                }
            }
        return stringBuilder.toString();
    }

    public void readCSV(String filePath, Class<?> type){
        management.get(type).readFile(filePath);
    }

    public void exportPhase4(String filePath){
        List<Student> students = getList(Student.class);
        CandidacyManager manager = (CandidacyManager) management.get(Candidacy.class);
        try(PrintWriter printWriter = new PrintWriter(filePath)) {
            for (Student student : students){
                printWriter.println(student.exportCSV());
                Candidacy candidacy = manager.find(student.getId(),Candidacy.class);
                printWriter.println(candidacy != null ? candidacy.exportCSV() : "non");
                FinalProposal finalProposal = manager.find(student.getId(),FinalProposal.class);
                printWriter.println(finalProposal != null ? finalProposal.exportPhase4() : "non");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean lockConfigurationPhase(){
        StudentManager student = (StudentManager) management.get(Student.class);
        ProposalManager proposal = (ProposalManager) management.get(Proposal.class);
        return student.branchCount("RAS") >= proposal.branchCount("RAS") &&
                student.branchCount("SI") >= proposal.branchCount("SI") &&
                student.branchCount("DA") >= proposal.branchCount("DA");
    }

    //Phase 3
    public String getListOfStudentsFinal(){
        StudentManager manager = (StudentManager) management.get(Student.class);
        return "With candidacy\n" + manager.getStudentsWithCandidacy() +
                "With SelfProposal\n" + manager.getStudentsWithSelfProposal() +
                "With no proposal\n" + manager.getStudentsWithNoProposal();
    }

    public String getListProposalsFinal(List<Integer> filters){
        StringBuilder builder = new StringBuilder();
        ProposalManager manager = (ProposalManager) management.get(Proposal.class);
        for (int i : filters){
            switch (i){
                case 1 -> builder.append("SelfProposal\n").append("-".repeat(20)).append("\n").append(manager.getSelfProposalList());
                case 2 -> builder.append("Project\n").append("-".repeat(20)).append("\n").append(manager.getProjectList());
                case 3 -> builder.append("Available\n").append("-".repeat(20)).append("\n").append(manager.getProposalsAvailable());
                case 4 -> builder.append("Attributed\n").append("-".repeat(20)).append("\n").append(manager.getProposalsAttributed());
            }
        }
        return builder.toString();
    }

    public void manualProposalAttribution(String proposalID,long studentID){
        FinalProposalManager proposal = (FinalProposalManager) management.get(FinalProposal.class);
        proposal.manualAttribution(proposalID,studentID);
    }

    public void manualProposalRemoveAttribution(String proposalID) {
        FinalProposalManager proposals = (FinalProposalManager) management.get(FinalProposal.class);
        if(!proposals.manuelRemove(proposalID)){
            Log.getInstance().addMessage("The proposal id or the student does not exit");
        }
    }

    public boolean automaticAttribution(){
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.automaticAttribution();
    }

    public void automaticAssignmentForProjectAndInterShip() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        manager.automaticAssignmentForProjectAndInterShip();
    }

    public void exportPhase3(String filePath){
        List<Student> students = getList(Student.class);
        CandidacyManager manager = (CandidacyManager) management.get(Candidacy.class);
        try(PrintWriter printWriter = new PrintWriter(filePath)) {
            for (Student student : students){
                printWriter.println(student.exportCSV());
                Candidacy candidacy = manager.find(student.getId(),Candidacy.class);
                printWriter.println(candidacy != null ? candidacy.exportCSV() : "non");
                FinalProposal finalProposal = manager.find(student.getId(),FinalProposal.class);
                printWriter.println(finalProposal != null ? finalProposal.exportPhase3() : "non");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean lockProposalPhase(){
        List<Candidacy> candidacies = new ArrayList<>(management.get(Candidacy.class).getList());
        List<FinalProposal> proposals = new ArrayList<>(management.get(FinalProposal.class).getList());
        int count = 0;
        for (Candidacy candidacy : candidacies){
            if(proposals.contains(FinalProposal.getFakeFinalproposal(candidacy.getStudentId())))
                count++;
        }

        return candidacies.size() == count;
    }

    public String getAttributionTeacherData() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        TeacherManager teacherManager = (TeacherManager) management.get(Teacher.class);
        return "List of students with teacher associated\n" + manager.getFinalProposalWithTeacher() +
                "List of students without teacher associated\n" + manager.getFinalProposalWithoutTeacher() +
                "Average: " + teacherManager.average() + " Max: " + teacherManager.highest() + " Min: " + teacherManager.lowest();
    }

    public void automaticTeacherAttribution() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        manager.automaticTeacherAttribution();
    }

    public String getData() {
        FinalProposalManager finalManager = (FinalProposalManager) management.get(FinalProposal.class);
        StudentManager studentManager = (StudentManager) management.get(Student.class);
        TeacherManager teacherManager = (TeacherManager) management.get(Teacher.class);
        ProposalManager proposalManager = (ProposalManager) management.get(Proposal.class);
        return "List of students with attributed proposal\n" + studentManager.getStudentsWithAssignedProposal() +
                "List of students without attributed proposal but with candidacy options\n" + studentManager.getStudentsWithoutFinalProposalAndWithCandidacy() +
                "Available Proposals\n" + proposalManager.getProposalsAvailable() +
                "Proposals Attributed\n" + finalManager.listOfFinalProposals() +
                "Average: " + teacherManager.average() + " Max: " + teacherManager.highest() + " Min: " + teacherManager.lowest();
    }

    //Phase 4
    public boolean manualTeacherAttribution(String proposalID, String teacherID) {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.manualTeacherAttribution(proposalID,teacherID);
    }

    public String teacherQuerying(){
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.getFinalProposalWithTeacher();
    }

    public boolean manualTeacherRemove(String proposalID) {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.manualTeacherRemove(proposalID);
    }

    public String getTieBreakerList() {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.getTieBreakerList();
    }

    public boolean handleConflict(long studentId, String proposalId) {
        FinalProposalManager manager = (FinalProposalManager) management.get(FinalProposal.class);
        return manager.tieHandleConflict(studentId,proposalId);
    }
}