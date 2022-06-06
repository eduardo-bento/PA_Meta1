package pt.isec.pa.apoio_poe.model.Data;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Data.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProposalManager extends Manager<Proposal> {
    private final Map<Class<?>, Set<Proposal>> listOfProposals;
    public ProposalManager(Data data) {
        super(data);
        listOfProposals = Map.of(
                SelfProposal.class,new HashSet<>(),
                Project.class,new HashSet<>(),
                InterShip.class,new HashSet<>()
        );
    }

    @Override
    public void readFile(String filePath){
        List<Proposal> items = new ArrayList<>();

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                String label = input.next();
                String id = input.next();
                switch (label){
                    case "T1" -> {
                        String destiny = input.next();
                        String title = input.next();
                        String entity = input.next();
                        long studentId = -1;
                        if (input.hasNextLong()){
                            studentId = input.nextLong();
                        }
                        items.add(new InterShip(id,title,studentId,destiny,entity));
                    }
                    case "T2" -> {
                        String destiny = input.next();
                        String title = input.next();
                        String teacher = input.next();
                        long studentId = -1;
                        if (input.hasNextLong()){
                            studentId = input.nextLong();
                        }
                        items.add(new Project(id,title,studentId,destiny,teacher));
                    }
                    case "T3" -> {
                        String title = input.next();
                        long studentId = input.nextLong();
                        items.add(new SelfProposal(id,title,studentId));
                    }
                }
            }
        }  catch (FileNotFoundException e){
            Log.getInstance().addMessage("The file does not exist");
        } catch (NoSuchElementException e){
            Log.getInstance().addMessage("Error reading");
        }
        items.forEach(this::insert);
    }

    @Override
    public boolean insert(Proposal item) {
        if (item instanceof Project && !verifyProject((Project)item)){
            return false;
        } else if (item instanceof InterShip && !verifyInterShip((InterShip) item)){
            return false;
        } else if(item instanceof SelfProposal && !verifySelfProposal((SelfProposal) item)){
            return false;
        }

        if(super.insert(item)){
            listOfProposals.get(item.getClass()).add(item);
            return true;
        }
        return false;
    }

    private boolean verifyInterShip(InterShip interShip){
        if (!equal(interShip.getDestiny(),branches)){
            Log.getInstance().addMessage(interShip.getDestiny() + " is not a valid branch");
            return false;
        }
        if (interShip.getStudent() != -1){
            return find(interShip.getStudent(),Student.class) != null;
        }
        return true;
    }

    private boolean verifyProject(Project project){
        if (!equal(project.getDestiny(),branches)){
            Log.getInstance().addMessage(project.getDestiny() + " is not a valid branch");
            return false;
        }
        if (project.getStudent() != -1){
            if(find(project.getStudent(), Student.class) == null){
                return false;
            }
        }
        return find(project.getTeacher(), Teacher.class) != null;
    }

    private boolean verifySelfProposal(SelfProposal selfProposal){
        Student student = find(selfProposal.getStudent(),Student.class);
        if (student != null){
            return !findStudentInProposal(student.getId());
        }
        return false;
    }

    private boolean findStudentInProposal(long studentID){
        for (Proposal proposal : list){
            if (proposal.getStudent() == studentID){
                Log.getInstance().addMessage("That student: " + proposal.getStudent() + " is already assigned to a proposal");
                return true;
            }
        }
        return false;
    }

    @Override
    public <K> boolean remove(K id, Class<?>... type) {
        Proposal proposal = Proposal.getFakeProposal((String) id);
        System.out.println();
        if(list.remove(proposal)){
            removeFromListOfProposals(proposal);
            return true;
        }
        return false;
    }

    private void removeFromListOfProposals(Proposal proposal){
        Set<Proposal> proposals = listOfProposals.get(InterShip.class);
        proposals.remove(proposal);
        proposals = listOfProposals.get(Project.class);
        proposals.remove(proposal);
        proposals = listOfProposals.get(SelfProposal.class);
        proposals.remove(proposal);
    }

    public Set<Proposal> getSpecific(Class<?> label){
        return listOfProposals.get(label);
    }

    public List<Proposal> getProposalsNotAssigned(){
        List<Proposal> proposals = new ArrayList<>();
        for(Proposal proposal : list){
            if (!proposal.isAssigned() && proposal.getStudent() == -1)
                proposals.add(proposal);
        }
        return proposals;
    }

    public String getSelfProposalList(){
        StringBuilder builder = new StringBuilder();
        listOfProposals.get(SelfProposal.class).forEach(selfProposal -> builder.append(selfProposal).append("\n"));
        return builder.toString();
    }

    public String getProjectList(){
        StringBuilder builder = new StringBuilder();
        listOfProposals.get(Project.class).forEach(project -> builder.append(project).append("\n"));
        return builder.toString();
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

    public String getProposalsAvailable(){
        StringBuilder builder = new StringBuilder();
        for (Proposal proposal : list){
            if (!proposal.isAssigned() && proposal.getStudent() == -1)
                builder.append(proposal).append("\n");
        }
        return builder.toString();
    }

    public String getProposalsAttributed(){
        StringBuilder builder = new StringBuilder();
        for (Proposal proposal : list){
            if (proposal.isAssigned())
                builder.append(proposal).append("\n");
        }
        return builder.toString();
    }

    public int branchCount(String branch){
        int count = 0;
        for (Proposal item : listOfProposals.get(Project.class)){
            Project project = (Project) item;
            if(project.getDestiny().contains(branch)) count++;
        }
        for (Proposal item : listOfProposals.get(InterShip.class)){
            InterShip interShip = (InterShip) item;
            if(interShip.getDestiny().contains(branch)) count++;
        }
        return count;
    }
}
