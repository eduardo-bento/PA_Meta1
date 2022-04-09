package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;

import java.util.Objects;

public class Student {
    private long id;
    private String name;
    private String email;
    private String acronymCurse;
    private String acronymBranch;
    private double classification;
    private boolean haveProject_Stage;

    public Student(long id, String name, String email, String acronymCurse, String acronymBranch, double classification, boolean haveProject_Stage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.acronymCurse = acronymCurse;
        this.acronymBranch = acronymBranch;
        this.classification = classification;
        this.haveProject_Stage = haveProject_Stage;
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
        Log.getInstance().addMessage("The name was changed");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Log.getInstance().addMessage("The email was changed");
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

    public boolean isHaveProject_Stage() {
        return haveProject_Stage;
    }

    public void setHaveProject_Stage(boolean haveProject_Stage) {
        if (this.haveProject_Stage == haveProject_Stage){
            Log.getInstance().addMessage("You tried to change the stage to" + haveProject_Stage + "but the atribute already has that value");
        }
        this.haveProject_Stage = haveProject_Stage;
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
        return "id :" + id +
                ", name: " + name + '\'' +
                ", email: " + email + '\'' +
                ", acronymCurse: " + acronymCurse + '\'' +
                ", acronymBranch: " + acronymBranch + '\'' +
                ", classification: " + classification +
                ", haveProject_Stage: " + haveProject_Stage;
    }

    public static String getTypes(){
        return "id," +
                "name," +
                "email," +
                "acronymCurse," +
                "acronymBranch," +
                "classification," +
                "haveProject_Stage";
    }
}
