package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
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
    private final Map<Class,Set> management;
    private final ConfigurationManager configuration;
    private final Set<Student> students;
    private final Set<Teacher> teachers;
    private final Set<Proposal> proposals;
    private final Set<Candidacy> candidacies;

    public Data() {
        currentMode = EManagement.STUDENTS;
        configuration = new ConfigurationManager();
        management = new HashMap<>();
        students = new HashSet<>();
        teachers = new HashSet<>();
        proposals = new HashSet<>();
        candidacies = new HashSet<>();
        management.put(Student.class,students);
        management.put(Teacher.class,teachers);
        management.put(Proposal.class,proposals);
        management.put(Candidacy.class, candidacies);
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
        Class typeClass = object.getClass();

        if (set.add(object)){
            Log.getInstance().addMessage(Utils.splitString(typeClass.getName(), "\\.") + " added");
            return true;
        }
        Log.getInstance().addMessage(Utils.splitString(typeClass.getName(), "\\.") + " was not added");
        return false;
    }

    public <T,K> K find(T entity,Class<K> typeClass){
        Set set = management.get(typeClass);

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
        Set set = management.get(typeClass);
        Object object =  find(entity,typeClass);
        if (object != null){
            if (set.remove(object)) {
                Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was successfully removed");
                return;
            }
            Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was not removed");
        }
    }

    public <T, K, A> void edit(T entity, K value, String label, Class<A> typeClass) {
        if (find(entity,typeClass) == null){
            return;
        }

        if (Student.class.isAssignableFrom(typeClass)){
            for (Student s : students){
                if (s.getId() == (long) entity){
                    changeAttribute(value,label,typeClass,s);
                }
            }
        } else if(Teacher.class.isAssignableFrom(typeClass)){
            for (Teacher s : teachers){
                if (s.getEmail().equals(entity)){
                    changeAttribute(value,label,typeClass,s);
                }
            }
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
        Set set = management.get(Utils.getSuperClass(typeClass));
        StringBuilder stringBuilder = new StringBuilder();

       for (Object o : set){
           stringBuilder.append(o.toString()).append("\n");
       }
       return stringBuilder.toString();
    }

    public boolean addProposal(long id,String idProposal){
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.addProposal(idProposal)){
            for (Proposal proposal : proposals){
                if (idProposal.equals(proposal.getId())){
                    proposal.addCandicy();
                }
            }
            Log.getInstance().addMessage("The proposal was added to the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not added to the candidacy");
        return false;
    }

    public boolean removeProposal(long id, String idProposal) {
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.removeProposal(idProposal)){
            for (Proposal proposal : proposals){
                if (idProposal.equals(proposal.getId())){
                    proposal.subCandicy();
                }
            }
            Log.getInstance().addMessage("The proposal was remove from the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not remove from the candidacy");
        return false;
    }

    public String getListOfStudents(){
        List<Long> withCandidacy = new ArrayList<>();
        List<Long> withoutCandidacy = new ArrayList<>();

        for (Student s : students){
            if (s.hasCandicy()){
                withCandidacy.add(s.getId());
            } else{
                withoutCandidacy.add(s.getId());
            }
        }
        return "With candidacy" + "\n" + withCandidacy + "\n" + "Without candidacy" + "\n" + withoutCandidacy;
    }

    public String getListProposals(List<Integer> filters){
        StringBuilder stringBuilder = new StringBuilder();
        for (int f : filters){
            switch (f){
                case 1 -> {
                    stringBuilder.append("SelfProposals").append("\n");
                    for (Proposal p : proposals){
                        if (p instanceof SelfProposal){
                            stringBuilder.append(p).append("\n");
                        }
                    }
                }
                case 2 ->{
                    stringBuilder.append("Teacher Proposals").append("\n");
                    for (Proposal p : proposals){
                        if (p instanceof Project){
                            stringBuilder.append(p).append("\n");
                        }
                    }
                }
                case 3 ->{
                    stringBuilder.append("Proposals with candidacy").append("\n");
                    for (Proposal p : proposals){
                        if (p.get_hasCandidacy() > 0){
                            stringBuilder.append(p).append("\n");
                        }
                    }
                }
                case 4 ->{
                    stringBuilder.append("Proposals without candidacy").append("\n");
                    for (Proposal p : proposals){
                        if (p.get_hasCandidacy() == 0 && !(p instanceof SelfProposal)){
                            stringBuilder.append(p).append("\n");
                        }
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public void automaticAssignment(){
       /* for (Proposal p : proposals){
            if (p instanceof SelfProposal){
                SelfProposal proposal = (SelfProposal) p;
                Candidacy candidacy = new Candidacy(proposal.getStudent());
                candidacy.addProposal(proposal.getId());
            }
        }*/
    }
}
