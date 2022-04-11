package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private long id;
    private String name;
    private String email;
    private String acronymCurse;
    private String acronymBranch;
    private double classification;
    private boolean project;




    public Student(long id, String name, String email, String acronymCurse, String acronymBranch, double classification, boolean haveProject_Stage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.acronymCurse = acronymCurse;
        this.acronymBranch = acronymBranch;
        this.classification = classification;
        this.project = haveProject_Stage;

        Flyweight.addStudentToBranch(acronymBranch);
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

    public String getAcronymCurse() {
        return acronymCurse;
    }

    public void setAcronymCurse(String acronymCurse) {
        this.acronymCurse = acronymCurse;
    }

    public String getAcronymBranch() {
        return acronymBranch;
    }

    public void setAcronymBranch(String acronymBranch) {
        this.acronymBranch = acronymBranch;
    }

    public double getClassification() {
        return classification;
    }

    public void setClassification(double classification) {
        this.classification = classification;
    }

    public boolean isProject() {
        return project;
    }

    public void setProject(boolean project) {
        if (this.project == project){
            Log.getInstance().addMessage("You tried to change the stage to" + project + "but the attribute already has that value");
        }
        this.project = project;
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
                "-name: " + name + "\n" +
                "-email: " + email + "\n" +
                "-acronymCurse: " + acronymCurse + "\n" +
                "-acronymBranch: " + acronymBranch + "\n" +
                "-classification: " + classification + "\n" +
                "-haveProject_Stage: " + project;
    }
}
