package pt.isec.pa.apoio_poe.text;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.data.Proposals.InterShip;
import pt.isec.pa.apoio_poe.data.Proposals.Project;
import pt.isec.pa.apoio_poe.data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.data.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.data.Student;
import pt.isec.pa.apoio_poe.data.Teacher;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.utils.Input;

import java.util.Locale;

public class UserInterface {
    private final Context context;

    public UserInterface(Context context) {
        this.context = context;
    }

    public void start(){
        while (true){
            Log.getInstance().reset();
            switch (context.getState()){
                case CONFIGURATION -> configuration();
                case CANDIDACY -> candicy();
            }
            System.out.println(Log.getInstance().toString());
        }

    }

    public void configuration(){
        if(Input.chooseOption("Chose - ","goCandicy","Insert,Edit,remove") == 1){
            context.goCandidacy();
            return;
        }

        EManagement management = EManagement.fromInteger(Input.readInt("Chose " + EManagement.getTypes()));
        context.changeManagementMode(management);

        switch (Input.chooseOption("Chose - " + management.name().toLowerCase(Locale.ROOT), "Insert", "Edit", "Remove", "goCandicy")) {
            case 1 -> {
                if (management == EManagement.STUDENTS) {
                    long id = Input.readLong("Id ");
                    Student student = new Student(id, "qwe", "qwe", "qwe", "qwe", 123123, true);
                    context.insert(student);
                } else if (management == EManagement.TEACHER) {
                    String name = Input.readString("Name: ", false);
                    String email = Input.readString("Email: ", true);
                    boolean advisor = Input.readBoolean("Is advisor?");
                    Teacher teacher = new Teacher(email, name, advisor);
                    context.insert(teacher);
                } else if (management == EManagement.PROJECT_STAGE) {
                    switch (Input.chooseOption("Type of proposal", "InterShip", "Project", "SelfProposal")) {
                        case 1: {
                            String id = Input.readString("Id: ", false);
                            String title = Input.readString("Title: ", false);
                            String destiny = Input.readString("Destiny: ", false);
                            String entity = Input.readString("Entity: ", false);
                            long studentId = Input.readLong("Student id: ");

                            InterShip interShip = new InterShip(id, title, destiny, entity, studentId);
                            context.insert(interShip);
                        }
                        case 2: {
                            String id = Input.readString("Id: ", false);
                            String title = Input.readString("Title: ", false);
                            String destiny = Input.readString("Destiny: ", false);
                            String teacher = Input.readString("Teacher: ", false);
                            long studentId = Input.readLong("Student id: ");

                            Project project = new Project(id, title, destiny, teacher, studentId);
                            context.insert(project);
                        }
                        case 3: {
                            String id = Input.readString("Id: ", false);
                            String title = Input.readString("Title: ", false);
                            long studentId = Input.readLong("Student id: ");

                            SelfProposal selfProposal = new SelfProposal(id, title, studentId);
                            context.insert(selfProposal);
                        }
                    }
                }
            }
            case 2 -> {
                if (management == EManagement.STUDENTS) {
                    long id = Input.readLong("Id ");
                    switch (Input.chooseOption("Witch atribute you want to change", Student.getTypes())) {
                        case 1 -> context.edit(id, Input.readString("New name: ", false), "name");
                        case 2 -> context.edit(id, Input.readString("New email: ", false), "email");
                        case 3 -> context.edit(id, Input.readString("New acronym Curse: ", false), "curse");
                        case 4 -> context.edit(id, Input.readString("New acronym branch: ", false), "branch");
                        case 5 -> context.edit(id, Input.readNumber("New classification: "), "classification");
                        case 6 -> context.edit(id, Input.readBoolean("Have stage/don´t have stage: "), "stage");
                    }
                } else if (management == EManagement.TEACHER) {
                    String email = Input.readString("Email ", true);
                    switch (Input.chooseOption("Witch atribute you want to change", "name", "advisor -> true/false")) {
                        case 1 -> context.edit(email, Input.readString("New name: ", false), "name");
                        case 2 -> context.edit(email, Input.readBoolean("Is advidor?:"), "advisor");
                    }
                }
            }
            case 3 -> {
                if (management == EManagement.STUDENTS) {
                    long id = Input.readLong("Id ");
                    context.remove(id);
                } else if (management == EManagement.TEACHER) {
                    String email = Input.readString("Email: ", false);
                    context.remove(email);
                }
            }
        }
    }

    public void candicy(){
        if(Input.readBoolean("go back to configuration")){
            context.backConfiguration();
        }
    }
}
