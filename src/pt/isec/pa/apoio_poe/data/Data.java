package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Manager.*;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Candidacy;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Teacher;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })

public class Data {
    private EState currentMode;
    private final boolean[] phasesLock;
    private final Map<Class<?>, Manager> management;

    public Data() {
        currentMode = EState.STUDENT;
        management = Map.of(
                Student.class,new StudentManager(this),
                Teacher.class,new TeacherManager(this),
                Proposal.class,new ProposalManager(this),
                Candidacy.class,new CandidacyManager(this)
        );

        phasesLock = new boolean[5];
    }

    public Map<Class<?>, Manager> getManagement() {
        return management;
    }

    public EState getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(EState currentMode) {
        this.currentMode = currentMode;
    }

    public boolean isPhaseLock(EState state){
        return phasesLock[state.ordinal() - 1];
    }

    public boolean lockPhase(pt.isec.pa.apoio_poe.fsm.EState state){
        return phasesLock[state.ordinal()] = true;
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

    public String getListOfStudents(){
        StudentManager studentManager = (StudentManager) management.get(Student.class);
        return studentManager.getListOfStudents(management.get(Proposal.class).getList());
    }

    public String getListProposals(List<Integer> filters){
        ProposalManager manager = (ProposalManager) management.get(Proposal.class);
        return manager.getListOfProposals(filters);
    }

    public void readCVS(String filePath,Class<?> type){
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
        ProposalManager proposals = (ProposalManager) management.get(Proposal.class);
        if(!proposals.manualAttribution(proposalID,studentID)){
            Log.getInstance().addMessage("The proposal id or the student does not exit");
        }
    }

    public void manualProposalRemoveAttribution(String proposalID) {
        ProposalManager proposals = (ProposalManager) management.get(Proposal.class);
        if(!proposals.manuelRemove(proposalID)){
            Log.getInstance().addMessage("The proposal id or the student does not exit");
        }
    }

    public void automaticAssignment(){
        CandidacyManager candidacy = (CandidacyManager) management.get(Candidacy.class);
        candidacy.automaticAssignment();
    }
}