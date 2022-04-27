package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Student;

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
                long id = input.nextLong();
                String name = input.next();
                String email = input.next();
                String curse = input.next();
                String branch = input.next();
                double classification = input.nextDouble();
                String hasStage = input.next().toLowerCase(Locale.ROOT);
                boolean stage = hasStage.equals("true");

                items.add(new Student(id,name,email,curse,branch,classification,stage));
            }
        }  catch (FileNotFoundException e){
            Log.getInstance().addMessage("The file does not exist");
            e.printStackTrace();
        }

        items.forEach(this::insert);
    }

    @Override
    public boolean insert(Student item) {
        boolean classification = item.getClassification() > 0 && item.getClassification() < 1;
        if (classification && equal(item.getBranch(),branches) && equal(item.getCurse(),curses)){
            return super.insert(item);
        }
        return false;
    }

    public String getStudentsCandidacy(boolean label){
        StringBuilder stringBuilder = new StringBuilder();
        for (Student student : list){
            if (student.hasCandidacy() && label){
                stringBuilder.append(student).append("\n");
            } else if(!student.hasCandidacy() && !label){
                stringBuilder.append(student).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String getStudentWithSelfProposal(){
        StringBuilder stringBuilder = new StringBuilder();
        Set<Proposal> proposals = data.getSelfProposalSet();
        for (Proposal proposal : proposals){
            Student student = find(proposal.getId(),Student.class);
            if(student != null){
                stringBuilder.append(student).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public int branchCount(String branch){
        int count = 0;
        for (Student item : list){
            if(item.getBranch().equals(branch)) count++;
        }
        return count;
    }
}
