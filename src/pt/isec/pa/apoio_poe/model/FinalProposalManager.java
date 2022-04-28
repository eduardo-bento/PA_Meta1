package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Student.StudentClassification;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student.Student;
import pt.isec.pa.apoio_poe.model.Teacher.Teacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FinalProposalManager extends Manager<FinalProposal> {
    public FinalProposalManager(Data data) {
        super(data);
    }
    final List<Student> tieBreakerStudents = new ArrayList<>();
    List<Student> toRemove = new ArrayList<>();

    @Override
    public boolean insert(FinalProposal item) {
        if(list.add(item)){
            Log.getInstance().addMessage("The proposal "  +  item.getProposal() + " was assigned");
            return true;
        }
        return false;
    }

    public void automaticAssignmentForProjectAndInterShip(){
        List<Proposal> proposals = data.getList(Proposal.class);
        for (Proposal p : proposals){
            if ((p instanceof SelfProposal || p instanceof Project) && p.getStudent() != -1){
                linkToStudent(p.getStudent());
                insert(new FinalProposal(p.getStudent(),p.getId(),1));
            }
        }
    }

    public boolean automaticAttribution(){
        List<Student> students = data.getListOfStudentsWithNoProposal();
        Collections.sort(students,new StudentClassification());
        Collections.reverse(students);
        for (int i = 0; i < students.size(); i++){
            int count = equalsClassification(students,students.get(i));
            if (count != 0){
                tieBreakerInfo(students,i,count);
                return false;
            }
            if(!list.contains(FinalProposal.getFakeFinalproposal(students.get(i).getId()))){
                Candidacy candidacy = find(students.get(i).getId(),Candidacy.class);
                if (candidacy != null){
                    List<String> proposals = candidacy.getProposals();
                    for (int j = 0;  j < proposals.size(); j++){
                        if (!findProposal(proposals.get(j))){
                            linkToStudent(students.get(i).getId());
                            insert(new FinalProposal(students.get(i).getId(), proposals.get(j),j+ 1));
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    private int equalsClassification(List<Student> students,Student student){
        int count = 0;
        for (Student s : students){
            if (student != s && student.getClassification() == s.getClassification())
                count++;
        }
        return count;
    }

    public boolean tieBreakerChange(long studentId,String proposalId){
        Proposal proposal = find(proposalId,Proposal.class);
        if (find(studentId,Student.class) == null && proposal == null) return false;

        Candidacy candidacy = find(studentId,Candidacy.class);
        FinalProposal finalProposal = find(studentId,FinalProposal.class);

        for (Student student : tieBreakerStudents){
            candidacy = find(student.getId(),Candidacy.class);
            if (proposalsAssigned(candidacy)){
                for (Proposal p : data.getList(Proposal.class)){
                    if (!p.isAssigned()){
                        Log.getInstance().addMessage("All of your proposals are already assigned so we attributed a random one");
                        insert(new FinalProposal(student.getId(),p.getId(),-1));
                        toRemove.add(Student.getFakeStudent(student.getId()));
                        p.setAssigned(true);
                        break;
                    }
                }
            }
        }

        if (finalProposal == null && !proposal.isAssigned()){
            insert(new FinalProposal(studentId,proposalId,getPosition(candidacy,proposalId)));
            tieBreakerStudents.remove(Student.getFakeStudent(studentId));
            proposal.setAssigned(true);
        } else {
            Log.getInstance().addMessage("The proposal is already attributed");
        }

        for (Student student : toRemove){
            tieBreakerStudents.remove(student);
        }

        return tieBreakerStudents.isEmpty();
    }

    private boolean proposalsAssigned(Candidacy candidacy){
        int count = 0;
        for (String id : candidacy.getProposals()){
            Proposal proposal = find(id,Proposal.class);
            if (proposal.isAssigned())
                count++;
        }
        return count == candidacy.getProposals().size();
    }

    private int getPosition(Candidacy candidacy,String proposalID){
        int count = 0;
        for (String proposal : candidacy.getProposals()){
            count++;
            if (proposal.equals(proposalID))
               break;
        }
        return count;
    }

    private void tieBreakerInfo(List<Student> students,int incialPos,int amout){
        for (int i = incialPos; i <= incialPos + amout; i++){
            tieBreakerStudents.add(students.get(i));
        }
    }

    public String getTieBreakerList(){
        StringBuilder builder = new StringBuilder();
        for (Student student : tieBreakerStudents){
            builder.append(student).append("\n");
            Candidacy candidacy = find(student.getId(),Candidacy.class);
            builder.append(candidacy.getProposals());
        }
        return builder.toString();
    }

    private boolean findProposal(String proposal){
        for (FinalProposal f : list){
            if (f.getProposal().equals(proposal))
                return true;
        }
        return false;
    }

    private void linkToStudent(long studentId){
        find(studentId,Student.class).setAssignedProposal(true);
    }

    public void manualAttribution(String proposalID,long studentID) {
        Student student = find(studentID,Student.class);
        Proposal proposal = find(proposalID,Proposal.class);
        if (student == null || proposal == null) return;

        if (proposal.getStudent() != -1) return;

        FinalProposal finalProposal = find(studentID,FinalProposal.class);
        if (finalProposal == null){
           insert(new FinalProposal(studentID,proposalID,-1));
        }
        proposal.setAssigned(true);
        linkToStudent(studentID);
    }

    public boolean manuelRemove(String proposalID){
        Proposal proposal = find(proposalID,Proposal.class);
        if (proposal == null || proposal instanceof SelfProposal || proposal.getStudent() == -1) return false;

        Student student = find(proposal.getStudent(),Student.class);
        list.remove(FinalProposal.getFakeFinalproposal(proposal.getStudent()));
        student.setAssignedProposal(false);
        proposal.setAssigned(false);
        return true;
    }

    public void automaticTeacherAttribution(){
        for (FinalProposal finalProposal : list){
            Proposal proposal = find(finalProposal.getProposal(),Proposal.class);
            if (proposal instanceof Project){
                finalProposal.setTeacher(((Project) proposal).getTeacher());
                Teacher teacher = find(((Project) proposal).getTeacher(),Teacher.class);
                teacher.addToAmount();
            }
        }
    }

    public boolean manualTeacherAttribution(String proposalID, String teacherID) {
        Teacher teacher = find(teacherID,Teacher.class);
        if (find(teacherID,Teacher.class) != null){
            FinalProposal proposal = find(proposalID,FinalProposal.class);
            if (proposal != null){
                proposal.setTeacher(teacherID);
                teacher.addToAmount();
                return true;
            }
        }
        return false;
    }

    public boolean manualTeacherRemove(String proposalID){
        FinalProposal proposal = find(proposalID,FinalProposal.class);
        if (proposal != null){
            Teacher teacher = find(proposal.getTeacher(),Teacher.class);
            proposal.setTeacher("");
            teacher.subToAmount();
            return true;
        }
        return false;
    }

    public String getFinalProposalWithTeacher(){
        StringBuilder builder = new StringBuilder();
        for (FinalProposal proposal : list){
            if (!proposal.getTeacher().isEmpty())
                builder.append(proposal).append("\n");
        }
        return builder.toString();
    }

    public String getFinalProposalWithoutTeacher(){
        StringBuilder builder = new StringBuilder();
        for (FinalProposal proposal : list){
            if (proposal.getTeacher().isEmpty()) {
                builder.append(proposal).append("\n");
            }
        }
        return builder.toString();
    }

    public String listOfFinalProposals(){
        StringBuilder builder = new StringBuilder();
        for (FinalProposal finalProposal : list){
            builder.append(finalProposal.getProposal()).append("\n");
        }
        return builder.toString();
    }
}
