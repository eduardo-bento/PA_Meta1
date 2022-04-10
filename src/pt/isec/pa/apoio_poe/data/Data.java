package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class Data {
    private final boolean[] phasesLock;
    private final Map<Class,Set> management;
    private final Set<Student> students;
    private final Set<Teacher> teachers;
    private final Set<Proposal> proposals;

    public Data() {
        management = new HashMap<>();
        students = new HashSet<>();
        teachers = new HashSet<>();
        proposals = new HashSet<>();
        management.put(Student.class,students);
        management.put(Teacher.class,teachers);
        management.put(Proposal.class,proposals);
        phasesLock = new boolean[5];
    }

    public boolean isPhaseLock(EState state){
        return phasesLock[state.ordinal()];
    }

    public void lockPhase(EState state){
        phasesLock[state.ordinal()] = true;
    }

    public <T> boolean insert(Object object, Class<T> typeClass){
        Set set = management.get(Utils.getSuperClass(typeClass));

        if (set.add(object)){
            Log.getInstance().addMessage(Utils.splitString(typeClass.getName(), "\\.") + " added");
            return true;
        }
        Log.getInstance().addMessage(Utils.splitString(typeClass.getName(), "\\.") + " was not added");
        return false;
    }

    public <T,K> boolean find(T entity,Class<K> typeClass){
        Set set = management.get(typeClass);
        Class<?> type = Utils.getFirstField(typeClass).getType();

        String className = Utils.splitString(typeClass.getName(),"\\.");

        try{
            Method method = typeClass.getMethod("getFake" + className,type);
            if (set.contains(method.invoke(null, entity))) {
                return true;
            }
            Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public <T,K> void remove(T entity,Class<K> typeClass){
        if (!find(entity,typeClass)){
            return;
        }

        Set set = management.get(typeClass);
        Class<?> type = Utils.getFirstField(typeClass).getType();

        String className = Utils.splitString(typeClass.getName(),"\\.");

        try{
            Method method = typeClass.getMethod("getFake" + className,type);
            if (set.remove(method.invoke(null, entity))) {
                Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was successfully removed");
            } else {
                Log.getInstance().addMessage("The " + Utils.splitString(typeClass.getName(), "\\.") + " was not removed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T, K, A> void edit(T entity, K value, String label, Class<A> typeClass) {
        if (!find(entity,typeClass)){
            return;
        }

        if (Student.class.isAssignableFrom(typeClass)){
            for (Student s : students){
                if (s.getId() == (long) entity){
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

    public <T> String querying(Class<T> typeClass){
        Set set = management.get(Utils.getSuperClass(typeClass));
        StringBuilder stringBuilder = new StringBuilder();

       for (Object o : set){
           stringBuilder.append(o.toString()).append("\n");
       }
       return stringBuilder.toString();
    }
}
