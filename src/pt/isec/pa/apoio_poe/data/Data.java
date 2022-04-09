package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.fsm.EState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {
    private final boolean[] phasesLock;
    private final Set<Student> students;
    private final Set<Teacher> teachers;
    private final List<Proposal> proposals;

    public Data() {
        students = new HashSet<>();
        teachers = new HashSet<>();
        proposals = new ArrayList<>();
        phasesLock = new boolean[5];
    }

    public boolean isPhaseLock(EState state){
        return phasesLock[state.ordinal()];
    }

    public void lockPhase(EState state){
        phasesLock[state.ordinal()] = true;
    }

    public boolean addStudent(Student student){
        return students.add(student);
    }

    public boolean addTeacher(Teacher teacher){
        return teachers.add(teacher);
    }

    public boolean findStudent(long id){
        if (students.contains(Student.getFakeStudent(id))){
            return true;
        }
        Log.getInstance().addMessage("The student with id: " + id + " was not found");
        return false;
    }

    public boolean findTeacher(String email) {
        if (teachers.contains(Teacher.getFakeTeacher(email))){
            return true;
        }
        Log.getInstance().addMessage("The teacher with email: " + email + " was not found");
        return false;
    }

    public void removeStudent(long id){
        if (students.remove(Student.getFakeStudent(id))){
            Log.getInstance().addMessage("The student with id: " + id + " was successfully removed");
        }
    }

    public void removeTeacher(String email) {
        if (teachers.remove(Teacher.getFakeTeacher(email))){
            Log.getInstance().addMessage("The teacher with email: " + email + " was successfully removed");
        }
    }

    public <K> void editStudent(long id,K value,String label){
       for (Student s : students){
           if (s.getId() == id){
               switch (label){
                   case "name" -> s.setName((String) value);
                   case "email" -> s.setEmail((String) value);
                   case "curse" -> s.setAcronymCurse((String) value);
                   case "branch" -> s.setAcronymBranch((String) value);
                   case "classification" -> s.setClassification((double) value);
                   case "stage" -> s.setHaveProject_Stage((boolean) value);
               }
               break;
           }
       }
    }

    public <K> void editTeacher(String id,K value,String label){
        for (Teacher t : teachers){
            if (t.getEmail().equals(id)){
                switch (label){
                    case "name" -> t.setName((String) value);
                    case "advisor" -> t.setAdvisor((boolean) value);
                }
                break;
            }
        }
    }
}
