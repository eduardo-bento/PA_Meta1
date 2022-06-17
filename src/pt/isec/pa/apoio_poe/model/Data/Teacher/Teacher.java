package pt.isec.pa.apoio_poe.model.Data.Teacher;

import pt.isec.pa.apoio_poe.model.Log;

import java.io.Serializable;
import java.util.*;

public class Teacher implements Serializable {
    private String email;
    private String name;
    private boolean advisor;
    private int _amount;

    public Teacher(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static Teacher getFakeTeacher(String email){
        return new Teacher(email,"----");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Log.getInstance().addMessage("Email changed");
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Log.getInstance().addMessage("Name changed");
        this.name = name;
    }

    public boolean isAdvisor() {
        return advisor;
    }

    public void setAdvisor(boolean advisor) {
        if (this.advisor == advisor){
            Log.getInstance().addMessage("You tried to change the advisor to" + advisor + "but the attribute already has that value");
        }
        this.advisor = advisor;
    }

    public int getAmount() {
        return _amount;
    }

    public void addToAmount() {
        _amount++;
    }

    public void subToAmount(){_amount--;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(email, teacher.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public String exportCSV(){
        return email + "," + name;
    }

    @Override
    public String toString() {
        return "Teacher: " + email + "\n" +
                "name: " + name;
    }
}
