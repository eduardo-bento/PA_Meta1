package pt.isec.pa.apoio_poe.model.Data;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StudentManager extends Manager<Student> {
    public StudentManager(Data data) {
        super(data);
    }

    @Override
    public void readFile(String filePath){
        List<Student> items = new ArrayList<>();

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                long id = 0;
                if (input.hasNextLong()) {
                    id = input.nextLong();
                } else {
                    Log.getInstance().addMessage(input.next() + " is not an id");
                    input.nextLine();
                    continue;
                }

                String name = input.next();
                String email = input.next();
                String curse = input.next();
                String branch = input.next();
                double classification = 0;
                if (input.hasNextDouble()){
                    classification = input.nextDouble();
                } else{
                    Log.getInstance().addMessage("the classification is not a double");
                    input.nextLine();
                    continue;
                }

                String hasStage = input.next().toLowerCase(Locale.ROOT);
                boolean stage;
                if (hasStage.equals("true") || hasStage.equals("false")){
                    stage = hasStage.equals("true");
                } else{
                    Log.getInstance().addMessage(hasStage + " is not a boolean");
                    continue;
                }
                items.add(new Student(id,name,email,curse,branch,classification,stage));
            }
        }  catch (FileNotFoundException e){
            Log.getInstance().addMessage("The file does not exist");
        } catch (NoSuchElementException e){
            Log.getInstance().addMessage("Error reading");
        }

        items.forEach(this::insert);
    }

    @Override
    public boolean insert(Student item) {
        boolean classification = item.getClassification() > 0 && item.getClassification() < 1;
        if (validate(item, classification)){
            return super.insert(item);
        }
        return false;
    }

    private boolean validate(Student item, boolean classification) {
        if (!classification){
            Log.getInstance().addMessage(item.getClassification() + " needs to be between [0,1]");
            return false;
        }
        if (!equal(item.getBranch(),branches)){
            Log.getInstance().addMessage(item.getBranch() + " is not a valid branch");
            return false;
        }
        if (!item.getEmail().contains("@isec.pt")){
            Log.getInstance().addMessage(item.getEmail() + " is not a valid email because is not from isec");
            return false;
        }
        if (!equal(item.getCurse(),curses)){
            Log.getInstance().addMessage(item.getCurse() + " is not a valid curse");
            return false;
        }
        return true;
    }

    //phase 1

    public int branchCount(String branch){
        int count = 0;
        for (Student item : list){
            if(item.getBranch().contains(branch)) count++;
        }
        return count;
    }

    //Phase 3
    public String getStudentsWithCandidacy(){
        StringBuilder builder = new StringBuilder();
        for (Student student : list){
            if (student.hasCandidacy())
                builder.append("-").append(student.getId()).append("\n");
        }
        return builder.toString();
    }

    public String getStudentsWithoutCandidacy(){
        StringBuilder builder = new StringBuilder();
        for (Student student : list){
            if (!student.hasCandidacy())
                builder.append("-").append(student.getId()).append("\n");
        }
        return builder.toString();
    }

    public String getStudentsWithNoProposal(){
        StringBuilder builder = new StringBuilder();
        for (Student student : list){
            if (!student.hasAssignedProposal())
                builder.append("-").append(student.getId()).append("\n");
        }
        return builder.toString();
    }

    public String getStudentsWithSelfProposal(){
        StringBuilder builder = new StringBuilder();
        List<Proposal> proposals = data.getSelfProposalList();
        for (Proposal proposal : proposals){
            builder.append("-").append(find(proposal.getStudent(),Student.class).getId()).append("\n");
        }
        return builder.toString();
    }

    //Phase 4
    public List<Student> getListOfStudentsWithNoProposals(){
        List<Student> students = new ArrayList<>();
        for (Student student : list){
            if (!student.hasAssignedProposal() && student.hasCandidacy())
                students.add(student);
        }
        return students;
    }

    //Phase 5
    public String getStudentsWithoutFinalProposalAndWithCandidacy(){
        StringBuilder builder = new StringBuilder();
        for (Student student : list){
           if (student.hasCandidacy() && student.hasAssignedProposal()){
               builder.append(student).append("\n");
           }
        }
        return builder.toString();
    }

    public String getStudentsWithAssignedProposal(){
        StringBuilder builder = new StringBuilder();
        for (Student student : list){
            if (student.hasAssignedProposal())
                builder.append(student).append("\n");
        }
        return builder.toString();
    }
}
