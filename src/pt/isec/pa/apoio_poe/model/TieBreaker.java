package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.FinalProposal.FinalProposal;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Student.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TieBreaker implements Serializable {
    final private FinalProposalManager manager;
    final private Data data;
    final private List<Student> students;
    final private List<Student> toRemove;

    public TieBreaker(FinalProposalManager manager,Data data) {
        this.manager = manager;
        this.data = data;
        toRemove = new ArrayList<>();
        students = new ArrayList<>();
    }

    public boolean findTie(List<Student> students,Student student){
        int withSameClassification = 0;
        for (Student s : students){
            if (student != s && student.getClassification() == s.getClassification())
                withSameClassification++;
        }

        if (withSameClassification != 0){
            createListWithInformation(students,student);
            checkStudentsProposals();
        }

        if (this.students.size() > 1){
            return true;
        }
        this.students.clear();
        return false ;
    }

    private void createListWithInformation(List<Student> students, Student student){
        for (Student s : students){
            if (student.getClassification() == s.getClassification())
                this.students.add(s);
        }
    }

    public boolean handleConflict(long studentId,String proposalId){
        Student student = manager.find(studentId,Student.class);
        Proposal proposal = manager.find(proposalId,Proposal.class);
        if (student == null || proposal == null)
            return false;

        if (manager.find(studentId, FinalProposal.class) != null)
            return false;

        if (!proposal.isAssigned())
            manager.insert(new FinalProposal(studentId,proposalId,-1));

        student.setAssignedProposal(true);
        proposal.setAssigned(true);
        students.remove(student);
        checkStudentsProposals();

        return students.isEmpty();
    }

    private void checkStudentsProposals(){
        for (Student s : students){
            if(allProposalsAreAssigned(manager.find(s.getId(), Candidacy.class).getProposals())){
                if (!data.getProposalsNotAssigned().isEmpty()){
                    Proposal proposal = data.getProposalsNotAssigned().get(0);
                    Log.getInstance().addMessage("The students was assigned with proposal " + proposal.getId());
                    manager.insert(new FinalProposal(s.getId(),proposal.getId(),-1));
                    proposal.setAssigned(true);
                    s.setAssignedProposal(true);
                    toRemove.add(s);
                }
            }
        }
        removeFromList();
    }

    private void removeFromList(){
        for (Student s : toRemove){
            students.remove(s);
        }
        toRemove.clear();
    }

    private boolean allProposalsAreAssigned(List<String> proposals){
        int count = 0;
        for (String id : proposals){
            if (manager.find(id, Proposal.class).isAssigned())
                count++;
        }
        return count == proposals.size();
    }

    public List<Student> getStudents() {
        return students;
    }
}
