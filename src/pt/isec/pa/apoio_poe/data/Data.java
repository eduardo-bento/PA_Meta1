package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })

public class Data {
    private EManagement currentMode;
    private final boolean[] phasesLock;
    private final Map<Class<?>,Manager> management;

    public Data() {
        currentMode = EManagement.STUDENTS;
        management = Map.of(
                Student.class,new StudentManager(),
                Teacher.class,new TeacherManager(),
                Proposal.class,new ProposalManager(),
                Candidacy.class,new CandidacyManager()
        );

        phasesLock = new boolean[5];
    }

    public EManagement getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(EManagement currentMode) {
        this.currentMode = currentMode;
    }

    public boolean isPhaseLock(EState state){
        return phasesLock[state.ordinal()];
    }

    public boolean lockPhase(EState state){
        return phasesLock[state.ordinal()] = true;
    }

    public boolean insert(Object item){
        Class<?> type = currentMode.getDataClass();

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

    public <T,K> boolean remove(T id, Class<K> type){
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

    public void readCVS(String filePath,Class<?> typeClass){
        List<Object> objects = null;
        try {
            objects = (List<Object>) typeClass.getMethod("readFile",String.class).invoke(null,filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        objects.forEach(object -> insert(object));
    }
}
