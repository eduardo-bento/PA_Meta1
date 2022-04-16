package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.model.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Data {
    private EManagement currentMode;
    private final boolean[] phasesLock;
    private final ProposalManager proposalManager;
    private final Map<Class<?>,Set<?>> management;
    private Map<Class<?>,Method> handleInsert;

    public Data() {
        proposalManager = new ProposalManager();
        currentMode = EManagement.STUDENTS;
        management = Map.of(
                Student.class,new HashSet<Student>(),
                Teacher.class,new HashSet<Teacher>(),
                Proposal.class,proposalManager.getProposals(),
                Candidacy.class,new HashSet<Candidacy>()
        );

        try {
            handleInsert = Map.of(
                    InterShip.class,ProposalManager.class.getMethod("checkInterShip", Object.class,Data.class),
                    Project.class, ProposalManager.class.getMethod("checkProject", Object.class, Data.class),
                    SelfProposal.class, ProposalManager.class.getMethod("checkSelfProposal", Proposal.class, Data.class)
                    );
        } catch (Exception e){
            e.printStackTrace();
        }

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

    public boolean insert(Object object){
        Set set = management.get(Utils.getSuperClass(object.getClass()));
        Class<?> typeClass = object.getClass();

        Method method = handleInsert.get(typeClass);
        if (method != null){
            try {
                if(!(boolean) method.invoke(proposalManager,object,this)){
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (set.add(object)){
            Log.getInstance().addMessage(Utils.splitString(typeClass.getName(), "\\.") + " added");
            return true;
        }
        Log.getInstance().addMessage(Utils.splitString(typeClass.getName(), "\\.") + " was not added");
        return false;
    }

    public <T,K> K find(T entity,Class<K> typeClass){
        Set<?> set = management.get(typeClass);

        Class<?> type = Utils.getFirstField(typeClass).getType();
        String className = Utils.splitString(typeClass.getName(),"\\.");

        try{
            Method method = typeClass.getMethod("getFake" + className,type);
            Object object = method.invoke(null, entity);
            for (var v : set){
                if(v.hashCode() == object.hashCode()){
                    return (K) v;
                }
            }

            Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T,K> void remove(T entity,Class<K> typeClass){
        Set<?> set = management.get(typeClass);
        Object object =  find(entity,typeClass);

        if (object != null){

            try {
               Method method = ProposalManager.class.getMethod("removeProposal", Class.class, Proposal.class);
                try {
                    method.invoke(proposalManager,typeClass,object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (set.remove(object)) {
                Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was successfully removed");
                return;
            }
            Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was not removed");
        }
    }

    public <T, K, A> void edit(T entity, K value, String label, Class<A> typeClass) {
        A dataStructure = find(entity,typeClass);
        if (dataStructure != null){
            changeAttribute(value,label,typeClass,dataStructure);
        }
    }

    private <K, A> void changeAttribute(K value, String label, Class<A> typeClass,Object s) {
        for (Field f : typeClass.getDeclaredFields()) {
            if (label.equals(f.getName())) {
                try {
                    Method method = typeClass.getDeclaredMethod("set" + Utils.capitalString(f.getName()), f.getType());
                    method.invoke(typeClass.cast(s), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public <T> String querying(Class<T> typeClass) {
        Set<?> set = management.get(Utils.getSuperClass(typeClass));
        StringBuilder stringBuilder = new StringBuilder();

       for (Object o : set){
           stringBuilder.append(o.toString()).append("\n");
       }
       return stringBuilder.toString();
    }

    public boolean checkCandidacy(Candidacy candidacy){
        if (find(candidacy.getStudentId(),Candidacy.class) == null){
            Student student = find(candidacy.getStudentId(),Student.class);
            if (student != null){
                student.set_hasCandidacy(true);
                return true;
            }
        }
        return false;
    }

    public boolean addProposal(long id,String idProposal){
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.addProposal(idProposal)){
            Proposal proposal = find(idProposal,Proposal.class);
            if (proposal == null) return false;

            proposal.addCandidacy();
            Log.getInstance().addMessage("The proposal was added to the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not added to the candidacy");
        return false;
    }

    public boolean removeProposal(long id, String idProposal) {
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.removeProposal(idProposal)){
            Proposal proposal = find(idProposal,Proposal.class);
            if (proposal == null) return false;

            proposal.subCandidacy();
            Log.getInstance().addMessage("The proposal was remove from the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not remove from the candidacy");
        return false;
    }

    public String getListOfStudents(){
        List<Long> withCandidacy = new ArrayList<>();
        List<Long> withoutCandidacy = new ArrayList<>();
        List<Long> selfProposal = new ArrayList<>();

        for (Object object : management.get(Proposal.class)){
            if (object instanceof SelfProposal){
                selfProposal.add(((SelfProposal) object).getStudent());
            }
        }

        for (Object object : management.get(Student.class)){
            Student s = (Student) object;
            if (s.hasCandicy()){
                withCandidacy.add(s.getId());
            } else{
                withoutCandidacy.add(s.getId());
            }
        }
        return "With candidacy" + "\n" + withCandidacy + "\n" + "Without candidacy" + "\n" + withoutCandidacy + "Self Proposal" + "\n" + selfProposal;
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

    public String getListProposals(List<Integer> filters){
       return proposalManager.getListOfProposals(filters);
    }
}
