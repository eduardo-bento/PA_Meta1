package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.io.FileReader;
import java.lang.reflect.Field;
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


    public static List<Object> readFile(String filePath){
        List<Object> data = new ArrayList<>();
        List<Object> students = new ArrayList<>();
        try {
            Scanner input = new Scanner(new FileReader(filePath));
            while(input.hasNextLine()){
                String line = input.nextLine();
                String[] parameters = line.split(",");

                data.add(Long.parseLong(parameters[0]));
                data.add(parameters[1]);
                data.add(parameters[2]);
                data.add(parameters[3]);
                data.add(parameters[4]);
                data.add(Double.parseDouble(parameters[5]));
                data.add(Boolean.parseBoolean(parameters[6]));

                EManagement management = EManagement.fromClass(Student.class);
                students.add(management.factory(data));
                data.clear();
            }
            input.close();
        }  catch (Exception e){
            Log.getInstance().addMessage("The file does not exist");
        }
        return students;
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
