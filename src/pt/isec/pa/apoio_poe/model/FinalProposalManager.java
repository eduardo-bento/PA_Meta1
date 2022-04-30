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

import java.util.Collections;
import java.util.List;

public class FinalProposalManager extends Manager<FinalProposal> {
    private final TieBreaker tieBreaker;

    public FinalProposalManager(Data data) {
        super(data);
        tieBreaker = new TieBreaker(this,data);
    }
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
            if (tieBreaker.findTie(students,students.get(i))){
                return false;
            }

            if(find(students.get(i).getId(),FinalProposal.class) == null){
                Candidacy candidacy = find(students.get(i).getId(),Candidacy.class);
                List<String> proposals = candidacy.getProposals();
                for (int j = 0;  j < proposals.size(); j++){
                    if (!findProposal(proposals.get(j))){
                        linkToStudent(students.get(i).getId());
                        find(proposals.get(j),Proposal.class).setAssigned(true);
                        insert(new FinalProposal(students.get(i).getId()
                                , proposals.get(j),j+ 1));
                        break;
                    }
                }
            }
        }
        return true;
    }

    public boolean tieHandleConflict(long studentId, String proposalId){
       return tieBreaker.handleConflict(studentId,proposalId);
    }

    public String getTieBreakerList(){
        StringBuilder builder = new StringBuilder();
        for (Student student : tieBreaker.getStudents()){
            builder.append(student).append("\n");
            for (String id : find(student.getId(),Candidacy.class).getProposals()){
                if(!find(id,Proposal.class).isAssigned())
                    builder.append(id).append(",");
            }
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
                System.out.println("The teacher " + teacher.getEmail() + " was assigned to the proposal" + proposal.getId());
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
