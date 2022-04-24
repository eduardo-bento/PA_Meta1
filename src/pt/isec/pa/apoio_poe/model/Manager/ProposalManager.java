package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;
import pt.isec.pa.apoio_poe.model.Teacher;

import java.lang.reflect.Method;
import java.util.*;

public class ProposalManager extends Manager<Proposal> {
    private final Map<Class<?>, Set<Proposal>> listOfProposals;
    private Map<Class<?>, Method> handleInsert;

    public ProposalManager(Data data) {
        super(data);
        listOfProposals = Map.of(
                SelfProposal.class,new HashSet<>(),
                Project.class,new HashSet<>(),
                InterShip.class,new HashSet<>()
        );

        try {
            handleInsert = Map.of(
                    InterShip.class,ProposalManager.class.getDeclaredMethod("verifyInterShip", InterShip.class),
                    Project.class, ProposalManager.class.getDeclaredMethod("verifyProject", Project.class),
                    SelfProposal.class, ProposalManager.class.getDeclaredMethod("verifySelfProposal", Proposal.class)
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Set<Proposal> getSpecific(Class<?> label){
        return listOfProposals.get(label);
    }

    public String getSelfProposalList(){
        StringBuilder stringBuilder = new StringBuilder();
        listOfProposals.get(SelfProposal.class).forEach(selfProposal -> stringBuilder.append(selfProposal).append("\n"));
        return stringBuilder.toString();
    }

    public String getProjectList(){
        StringBuilder stringBuilder = new StringBuilder();
        listOfProposals.get(Project.class).forEach(selfProposal -> stringBuilder.append(selfProposal).append("\n"));
        return stringBuilder.toString();
    }

    public String getProposalsWithCandidacy(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Proposal proposal : list){
           if(proposal.hasCandidacy()){
               stringBuilder.append(proposal).append("\n");
           }
        }
        return stringBuilder.toString();
    }

    public String getProposalsWithoutCandidacy(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Proposal proposal : list){
            if(!proposal.hasCandidacy()){
                stringBuilder.append(proposal).append("\n");
            }
        }
        return stringBuilder.toString();
    }



    @Override
    public boolean insert(Proposal item) {
        Method method = handleInsert.get(item.getClass());
        try {
            if(!(boolean) method.invoke(this,item)){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.insert(item);
        listOfProposals.get(item.getClass()).add(item);
        return true;
    }

    @Override
    public <K> boolean remove(K id, Class<?>... type) {
        Proposal proposal = Proposal.getFakeProposal((String) id);
        if(list.remove(proposal)){
            listOfProposals.get(type[0]).remove(proposal);
            return true;
        }
        return false;
    }

    public String getListOfProposals(List<Integer> filters){
        StringBuilder stringBuilder = new StringBuilder();
        for (int f : filters){
            switch (f){
                case 1 -> stringBuilder.append("SelfProposals").append("\n").append(listOfProposals.get(SelfProposal.class));
                case 2 -> stringBuilder.append("Teacher Proposals").append("\n").append(listOfProposals.get(Project.class));
                case 3 ->{
                    stringBuilder.append("Proposals with candidacy").append("\n");

                }
                case 4 ->{
                    stringBuilder.append("Proposals without candidacy").append("\n");
                    list.forEach( proposal ->{
                        if (!proposal.hasCandidacy()) stringBuilder.append(proposal).append("\n");
                    });
                }
            }
        }
        return stringBuilder.toString();
    }

    public int branchCount(String branch){
        int count = 0;
        for (Proposal item : listOfProposals.get(Project.class)){
            Project project = (Project) item;
            if(project.getDestiny().equals(branch)) count++;
        }
        for (Proposal item : listOfProposals.get(InterShip.class)){
            InterShip interShip = (InterShip) item;
            if(interShip.getDestiny().equals(branch)) count++;
        }
        return count;
    }

    public boolean manualAttribution(String proposalID,long studentID) {
        Student student = find(studentID,Student.class);
        Proposal proposal = find(proposalID,Proposal.class);
        if (student == null || proposal == null) return false;

        if (!student.getHasProposal()){
           proposal.setStudent(studentID);
        }
        return true;
    }

    public boolean manuelRemove(String proposalID){
        Proposal proposal = find(proposalID,Proposal.class);
        if (proposal == null || proposal instanceof SelfProposal || proposal.getStudent() == -1) return false;

        Student student = find(proposal.getStudent(),Student.class);
        student.setHasProposal(false);
        return true;
    }

    private boolean verifyInterShip(InterShip interShip){
        if (studentRegistered(interShip.getStudent())){
            Student student = find(interShip.getStudent(),Student.class);
            if (student != null){
                return !student.isHasStage();
            }
        }
        return true;
    }

    private boolean verifyProject(Project project){
        if (find(project.getTeacher(), Teacher.class) == null || !studentRegistered(project.getStudent())){
            return false;
        }
        return true;
    }

    private boolean verifySelfProposal(Proposal proposal){
        return true;
    }

    private boolean studentRegistered(long studentId){
        if (studentId != -1){
            return find(studentId,Student.class) != null;
        }
        return true;
    }

}
