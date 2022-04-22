package pt.isec.pa.apoio_poe.model.dataStrucutures;

import pt.isec.pa.apoio_poe.Log;

import java.io.File;
import java.util.*;

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

/*    public static List<Object> readFile(String filePath){
        List<Object> data = new ArrayList<>();
        List<Object> students = new ArrayList<>();
        EDataStructure management = EDataStructure.fromClass(Teacher.class);

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                data.add(input.next());
                data.add(input.next());

                String stageStr = input.next().toLowerCase(Locale.ROOT);
                boolean stage = switch (stageStr){
                    case "true" -> true;
                    default -> false;
                };

                data.add(stage);
                students.add(management.factory(data));
                data.clear();
            }
        }  catch (Exception e){
            Log.getInstance().addMessage("The file does not exist");
        }
        return students;
    }*/

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

    @Override
    public String toString() {
        return "Teacher: " +
                "email: " + email + "\n" +
                "name: " + name + "\n" +
                "advisor: " + advisor;
    }
}
