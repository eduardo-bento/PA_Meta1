package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.model.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;

import java.util.*;

public class ProposalManager {
    private final Map<Class<?>, Set<Proposal>> listOfProposals;
    private final Set<Proposal> proposals;

    public ProposalManager() {
        proposals = new HashSet<>();
        listOfProposals = Map.of(
                SelfProposal.class,new HashSet<>(),
                Project.class,new HashSet<>(),
                InterShip.class,new HashSet<>()
        );
    }

    public Set<Proposal> getProposals() {
        return proposals;
    }

    public Set<Proposal> get(Class<?> label){
        return listOfProposals.get(label);
    }

    public void addProposal(Class<?> label,Proposal proposal){
         listOfProposals.get(label).add(proposal);
    }

    public boolean removeProposal(Class<?> label,Proposal proposal){
         return listOfProposals.get(label).remove(proposal);
    }

    public boolean checkInterShip(Object proposal,Data data){
        InterShip interShip = (InterShip) proposal;
        if (studentRegistered(interShip.getStudent(),data)){
            Student student = data.find(interShip.getStudent(),Student.class);
            if (!student.isHasStage()){
                return false;
            }
        }
        addProposal(InterShip.class,interShip);
        return true;
    }

    public boolean checkProject(Object proposal,Data data){
        Project project = (Project) proposal;
        if (data.find(project.getTeacher(), Teacher.class) == null || !studentRegistered(project.getStudent(),data)){
            return false;
        }
        addProposal(Project.class,project);
        return true;
    }

    public boolean checkSelfProposal(Proposal proposal,Data data){
        return true;
    }

    private boolean studentRegistered(long studentId,Data data){
        if (studentId != -1){
            return data.find(studentId,Student.class) != null;
        }
        return true;
    }

    public String getListOfProposals(List<Integer> filters){
        StringBuilder stringBuilder = new StringBuilder();
        for (int f : filters){
            switch (f){
                case 1 -> stringBuilder.append("SelfProposals").append("\n").append(listOfProposals.get(SelfProposal.class));
                case 2 -> stringBuilder.append("Teacher Proposals").append("\n").append(listOfProposals.get(Teacher.class));
                case 3 ->{
                    stringBuilder.append("Proposals with candidacy").append("\n");
                    proposals.forEach( proposal ->{
                        if (proposal.get_hasCandidacy() > 0) stringBuilder.append(proposal).append("\n");
                    });
                }
                case 4 ->{
                    stringBuilder.append("Proposals without candidacy").append("\n");
                    proposals.forEach( proposal ->{
                        if (proposal.get_hasCandidacy() == 0) stringBuilder.append(proposal).append("\n");
                    });
                }
            }
        }
        return stringBuilder.toString();
    }
}
