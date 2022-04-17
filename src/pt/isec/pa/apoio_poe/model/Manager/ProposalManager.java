package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Project;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Teacher;

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
                    InterShip.class,ProposalManager.class.getDeclaredMethod("insertInterShip", InterShip.class),
                    Project.class, ProposalManager.class.getDeclaredMethod("insertProject", Project.class),
                    SelfProposal.class, ProposalManager.class.getDeclaredMethod("insertSelfProposal", Proposal.class)
            );
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Set<Proposal> getSpecific(Class<?> label){
        return listOfProposals.get(label);
    }

    @Override
    public boolean insert(Proposal item) {
        super.insert(item);

        Method method = handleInsert.get(item.getClass());
        try {
            if(!(boolean) method.invoke(this,item)){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private boolean insertInterShip(InterShip interShip){
        if (studentRegistered(interShip.getStudent())){
            Student student = find(interShip.getStudent(),Student.class);
            if (!student.isHasStage()){
                return false;
            }
        }
        insert(interShip);
        return true;
    }

    private boolean insertProject(Project project){
        if (find(project.getTeacher(), Teacher.class) == null || !studentRegistered(project.getStudent())){
            return false;
        }
        insert(project);
        return true;
    }

    private boolean insertSelfProposal(Proposal proposal){
        return true;
    }

    private boolean studentRegistered(long studentId){
        if (studentId != -1){
            return find(studentId,Student.class) != null;
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
                    list.forEach( proposal ->{
                        if (proposal.get_hasCandidacy() > 0) stringBuilder.append(proposal).append("\n");
                    });
                }
                case 4 ->{
                    stringBuilder.append("Proposals without candidacy").append("\n");
                    list.forEach( proposal ->{
                        if (proposal.get_hasCandidacy() == 0) stringBuilder.append(proposal).append("\n");
                    });
                }
            }
        }
        return stringBuilder.toString();
    }
}
