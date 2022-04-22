package pt.isec.pa.apoio_poe.model.dataStrucutures;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.fsm.EState;

import java.io.File;
import java.util.*;

public class Student {
    private long id;
    private String name;
    private String email;
    private String curse;
    private String branch;
    private double classification;
    private boolean hasStage;
    private boolean _hasCandidacy;
    private boolean _hasProposal;

    public Student(long id, String name, String email, String acronymCurse, String acronymBranch, double classification, boolean hasStage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.curse = acronymCurse;
        this.branch = acronymBranch;
        this.classification = classification;
        this.hasStage = hasStage;
    }

    public static Student getFakeStudent(long id){
        return new Student(id,"---","---","---","---",9999,true);
    }

    public boolean getHasProposal() {
        return _hasProposal;
    }

    public void setHasProposal(boolean hasProposal) {
        this._hasProposal = hasProposal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Log.getInstance().addMessage("The student name was changed");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Log.getInstance().addMessage("The student email was changed");
        this.email = email;
    }

    public String getCurse() {
        return curse;
    }

    public void setCurse(String curse) {
        this.curse = curse;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public double getClassification() {
        return classification;
    }

    public void setClassification(double classification) {
        this.classification = classification;
    }

    public boolean isHasStage() {
        return hasStage;
    }

    public boolean hasCandidacy() {
        return _hasCandidacy;
    }

    public void hasCandidacy(boolean candidacy) {
        this._hasCandidacy = candidacy;
    }

    public void setHasStage(boolean hasStage) {
        if (this.hasStage == hasStage){
            Log.getInstance().addMessage("You tried to change the stage to" + hasStage + "but the attribute already has that value");
        }
        this.hasStage = hasStage;
    }

    public static List<Object> readFile(String filePath){
        List<Object> data = new ArrayList<>();
        List<Object> items = new ArrayList<>();

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                data.add(input.nextLong());
                data.add(input.next());
                data.add(input.next());
                data.add(input.next());
                data.add(input.next());
                data.add(input.nextDouble());

                String stageStr = input.next().toLowerCase(Locale.ROOT);
                boolean stage = stageStr.equals("true");
                data.add(stage);

                items.add(EState.STUDENT.factory(data));
                data.clear();
            }
        }  catch (Exception e){
            Log.getInstance().addMessage("The file does not exist");
        }
        return items;
    }

    @Override
    public String toString() {
        return "\nStudent id :" + id + "\n" +
                "-Name: " + name + "\n" +
                "-Email: " + email + "\n" +
                "-Curse: " + curse + "\n" +
                "-Branch: " + branch + "\n" +
                "-Classification: " + classification + "\n" +
                "-Has project: " + hasStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
