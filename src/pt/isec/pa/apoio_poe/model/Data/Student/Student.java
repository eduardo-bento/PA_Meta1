package pt.isec.pa.apoio_poe.model.Data.Student;

import pt.isec.pa.apoio_poe.model.Log;

import java.io.Serializable;
import java.util.*;

public class Student implements Serializable {
    private long id;
    private String name;
    private String email;
    private String curse;
    private String branch;
    private double classification;
    private boolean hasStage;
    private boolean _hasCandidacy;
    private boolean _hasAssignedProposal;

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

    public boolean hasAssignedProposal() {
        return _hasAssignedProposal;
    }

    public void setAssignedProposal(boolean hasProposal) {
        this._hasAssignedProposal = hasProposal;
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
        if (email.contains("@isec.pt")){
            Log.getInstance().addMessage("Email changed");
            this.email = email;
        }
        Log.getInstance().addMessage("Email was not changed");
    }

    public String getCurse() {
        return curse;
    }

    public void setCurse(String curse) {
        if (curse.equals("LEI") || curse.equals("LEI-PL")){
            Log.getInstance().addMessage("Curse changed");
            this.curse = curse;
        }
        Log.getInstance().addMessage("Curse was not changed");
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        if (branch.equals("RAS") || branch.equals("SI") || branch.equals("DA")){
            Log.getInstance().addMessage("Branch changed");
            this.branch = branch;
        }
        Log.getInstance().addMessage("Branch was not changed");
    }

    public double getClassification() {
        return classification;
    }

    public void setClassification(double classification) {
        if (classification >= 0 && classification <= 1){

            this.classification = classification;
        }
        Log.getInstance().addMessage("The classification must be between [0,1]");
    }

    public boolean hasStage() {
        return hasStage;
    }

    public boolean hasCandidacy() {
        return _hasCandidacy;
    }

    public void setCandidacy(boolean candidacy) {
        this._hasCandidacy = candidacy;
    }

    public void setHasstage(boolean hasStage) {
        if (this.hasStage == hasStage){
            Log.getInstance().addMessage("You tried to change the stage to" + hasStage + "but the attribute already has that value");
        }
        this.hasStage = hasStage;
    }

    public String exportCSV(){
        return id + "," + name + "," + email + "," +
                curse + "," + branch + "," + classification + "," +
                hasStage;
    }

    @Override
    public String toString() {
        return "Student id :" + id + "\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Curse: " + curse + "\n" +
                "Branch: " + branch + "\n" +
                "Classification: " + classification + "\n" +
                "Has stage: " + hasStage;
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
