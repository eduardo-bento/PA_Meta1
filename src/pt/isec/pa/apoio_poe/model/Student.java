package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.Log;

import java.util.Objects;

public class Student {
    private long id;
    private String name;
    private String email;
    private String curse;
    private String branch;
    private double classification;
    private boolean hasStage;
    private boolean _hasCandidacy;

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

    public boolean hasCandicy() {
        return _hasCandidacy;
    }

    public void set_hasCandidacy(boolean candidacy) {
        this._hasCandidacy = candidacy;
    }

    public void setHasStage(boolean hasStage) {
        if (this.hasStage == hasStage){
            Log.getInstance().addMessage("You tried to change the stage to" + hasStage + "but the attribute already has that value");
        }
        this.hasStage = hasStage;
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
}