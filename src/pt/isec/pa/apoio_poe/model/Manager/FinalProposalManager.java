package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.StudentClassification;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.model.FinalProposal;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FinalProposalManager extends Manager<FinalProposal> {
    public FinalProposalManager(Data data) {
        super(data);
    }

    public void automaticProposals(){
        List<Proposal> proposals = new ArrayList<>(data.getManagement().get(Proposal.class).getList());
        for (Proposal p : proposals){
            if ((p instanceof SelfProposal || p instanceof Project) && p.getStudent() != -1){
                list.add(new FinalProposal(p.getStudent(),p.getId()));
            }
        }
    }

    public String getListOfStudents() {
        StringBuilder stringBuilder = new StringBuilder();
        ProposalManager proposalManager = (ProposalManager) data.getManagement().get(Proposal.class);
        stringBuilder.append("\nWith SelfProposal\n").append("\n");
        proposalManager.getSpecific(SelfProposal.class).forEach(proposal -> stringBuilder.append(find(proposal.getStudent(),Student.class)));


        stringBuilder.append("\nWith candidacy\n");
        for (Student student : (Set<Student>) data.getManagement().get(Student.class).getList()){
            if (student.hasCandidacy()){
                stringBuilder.append(student).append("\n");
            }
        }
        //todo: tem proposta atribuida

        stringBuilder.append("\nWith no proposals\n");
        for (Student student : (Set<Student>) data.getManagement().get(Student.class).getList()){
            if(!list.contains(FinalProposal.getFakeFinalproposal(student.getId()))){
                stringBuilder.append(student).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public void automaticAttribution(){
        List<Student> students = new ArrayList<>(data.getManagement().get(Student.class).getList());

        Collections.sort(students,new StudentClassification());
        Collections.reverse(students);

        for (Student student : students){
            if(!list.contains(FinalProposal.getFakeFinalproposal(student.getId()))){
                Candidacy candidacy = find(student.getId(),Candidacy.class);
                if (candidacy != null){
                    for (String proposal : candidacy.getProposals()){
                        if (!findProposal(proposal)){
                            list.add(new FinalProposal(student.getId(),proposal));
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean findProposal(String proposal){
        for (FinalProposal f : list){
            if (f.getProposal().equals(proposal))
                return true;
        }
        return false;
    }

    public String getListOfProposals(List<Integer> filters) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int f : filters){
            switch (f){
                case 1 -> stringBuilder.append("SelfProposals").append("\n").append(((ProposalManager) data.getManagement().get(Proposal.class)).getSpecific(SelfProposal.class));
                case 2 -> stringBuilder.append("Teacher Proposals").append("\n").append(((ProposalManager) data.getManagement().get(Proposal.class)).getSpecific(Teacher.class));
                case 3 ->{
                    stringBuilder.append("Available proposals").append("\n");
                    List<Proposal> proposals = new ArrayList<>(data.getManagement().get(Proposal.class).getList());
                    for (Proposal proposal : proposals){
                        if (!list.contains(FinalProposal.getFakeFinalproposal(proposal.getStudent()))){
                            stringBuilder.append(proposal).append("\n");
                        }
                    }
                }
                case 4 -> {
                    stringBuilder.append("Proposals already attributed").append("\n");
                    list.forEach(print -> stringBuilder.append(print).append("\n"));
                }
            }
        }
        return stringBuilder.toString();
    }

    public boolean manualAttribution(String proposalID,long studentID) {
        Student student = find(studentID,Student.class);
        Proposal proposal = find(proposalID,Proposal.class);
        if (student == null || proposal == null) return false;

        FinalProposal finalProposal = find(studentID,FinalProposal.class);
        if (finalProposal == null){
           list.add(new FinalProposal(studentID,proposalID));
        }

        if (!student.getHasProposal()){
            proposal.setStudent(studentID);
        }
        return true;
    }

    public boolean manuelRemove(String proposalID){
        Proposal proposal = find(proposalID,Proposal.class);
        if (proposal == null || proposal instanceof SelfProposal || proposal.getStudent() == -1) return false;

        Student student = find(proposal.getStudent(),Student.class);
        list.remove(FinalProposal.getFakeFinalproposal(proposal.getStudent()));
        student.setHasProposal(false);
        return true;
    }

    public String getProposalTeacher(boolean label){
        StringBuilder stringBuilder = new StringBuilder();
        for (FinalProposal proposal : list){
            if (proposal.getTeacher().isEmpty() && !label)
                stringBuilder.append(proposal).append("\n");
            else if(!proposal.getTeacher().isEmpty() && label)
                stringBuilder.append(proposal).append("\n");
        }
        return stringBuilder.toString();
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

    public void attributeATeacher(String proposalID, String teacherID) {
        Teacher teacher = find(teacherID,Teacher.class);
        if (find(teacherID,Teacher.class) != null){
            FinalProposal proposal = find(proposalID,FinalProposal.class);
            if (proposal != null){
                proposal.setTeacher(teacherID);
                teacher.addToAmount();
            }
        }
    }

    public String listOfStudentsWithFinalProposal(){
        StringBuilder stringBuilder = new StringBuilder();
        for (FinalProposal finalProposal : list){
            stringBuilder.append(finalProposal.getStudentId()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String listOfStudentsWithoutFinalProposalAndWithCandidacy(){
        StringBuilder stringBuilder = new StringBuilder();
        for (FinalProposal finalProposal : list){
            Student student = find(finalProposal.getStudentId(),Student.class);
            if (student.hasCandidacy()){
                stringBuilder.append(finalProposal.getStudentId()).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String listOfAvailableProposals(){
        StringBuilder stringBuilder = new StringBuilder();
        boolean add = true;
        Set<Proposal> proposals = data.getList(Proposal.class);
        for (Proposal proposal : proposals){
            for (FinalProposal finalProposal : list){
                if (proposal.getId().equals(finalProposal.getProposal())){
                    add = false;
                }
            }
            if(add){
                stringBuilder.append(proposal.getId()).append("\n");
            }
            add = true;
        }
        return stringBuilder.toString();
    }

    public String listOfFinalProposals(){
        StringBuilder stringBuilder = new StringBuilder();
        for (FinalProposal finalProposal : list){
            stringBuilder.append(finalProposal.getProposal()).append("\n");
        }
        return stringBuilder.toString();
    }
}
