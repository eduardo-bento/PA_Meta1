package pt.isec.pa.apoio_poe.data;

import pt.isec.pa.apoio_poe.Log;

import java.util.Objects;

public class Teacher {
    private String email;
    private String name;
    private boolean advisor;

    public Teacher(String email, String name,boolean advisor) {
        this.email = email;
        this.name = name;
        this.advisor = advisor;
    }

    public static Teacher getFakeTeacher(String email){
        return new Teacher(email,"----",true);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
}
