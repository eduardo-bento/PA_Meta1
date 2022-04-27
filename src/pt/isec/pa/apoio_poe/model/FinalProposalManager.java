package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Student.StudentClassification;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student.Student;
import pt.isec.pa.apoio_poe.model.Teacher.Teacher;

import java.util.Collections;
import java.util.List;

public class FinalProposalManager extends Manager<FinalProposal> {
    public FinalProposalManager(Data data) {
        super(data);
    }

    public void automaticAssignmentForProjectAndInterShip(){
        List<Proposal> proposals = data.getList(Proposal.class);
        for (Proposal p : proposals){
            if ((p instanceof SelfProposal || p instanceof Project) && p.getStudent() != -1){
                list.add(new FinalProposal(p.getStudent(),p.getId(),1));
            }
        }
    }

    public boolean automaticAttribution(){
        List<Student> students = data.getList(Student.class);
        Collections.sort(students,new StudentClassification());
        Collections.reverse(students);
        for (Student student : students){
            if(!list.contains(FinalProposal.getFakeFinalproposal(student.getId()))){
                Candidacy candidacy = find(student.getId(),Candidacy.class);
                if (candidacy != null){
                    List<String> proposals = candidacy.getProposals();
                    for (int i = 0;  i < proposals.size(); i++){
                        if (!findProposal(proposals.get(i))){
                            linkToStudent(student.getId());
                            list.add(new FinalProposal(student.getId(), proposals.get(i),i + 1));
                            break;
                        }
                    }
                }
            }
        }
        return true;
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

    public boolean manualAttribution(String proposalID,long studentID) {
        Student student = find(studentID,Student.class);
        Proposal proposal = find(proposalID,Proposal.class);
        if (student == null || proposal == null) return false;

        FinalProposal finalProposal = find(studentID,FinalProposal.class);
        if (finalProposal == null){
           list.add(new FinalProposal(studentID,proposalID,-1));
        }

        proposal.setAssigned(true);
        student.setAssignedProposal(true);

        return true;
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
