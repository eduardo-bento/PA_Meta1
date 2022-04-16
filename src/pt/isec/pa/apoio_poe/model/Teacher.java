package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.EManagement;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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

    public static List<Object> readFile(String filePath){
        List<Object> data = new ArrayList<>();
        List<Object> objects = new ArrayList<>();
        try {
            Scanner input = new Scanner(new FileReader(filePath));
            while(input.hasNextLine()){
                String line = input.nextLine();
                String[] parameters = line.split(",");

                data.add(parameters[0]);
                data.add(parameters[1]);
                data.add(Boolean.parseBoolean(parameters[2]));
                EManagement management = EManagement.fromClass(Teacher.class);
                objects.add(management.factory(data));
                data.clear();
            }
            input.close();
        }  catch (Exception e){
            Log.getInstance().addMessage("The file does not exist");
        }
        return objects;
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
