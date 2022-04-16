package pt.isec.pa.apoio_poe.text;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Commands;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.utils.Input;
import pt.isec.pa.apoio_poe.utils.Tuple;
import pt.isec.pa.apoio_poe.utils.Utils;

import javax.swing.text.Style;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
                case CANDIDACY -> candidacy();
                case PROPOSALS -> proposals();
            }
            System.out.println(Log.getInstance().toString());
        }
    }

    public void configuration(){
        Commands commands = new Commands();
        Object object;
        List<Object> data = new ArrayList<>();
        EManagement management = context.getManagementMode();
        Field[] myClass = management.getDataClass().getDeclaredFields();
        Field[] superClassFields = management.getDataClass().getSuperclass().getDeclaredFields();
        List<Field> fields = new ArrayList<>(myClass.length + superClassFields.length);
        Collections.addAll(fields,superClassFields);
        Collections.addAll(fields,myClass);

        switch (Input.chooseOption("State - " + context.getState().toString().toLowerCase(Locale.ROOT) +
                        "\ncurrent mode: " + context.getManagementMode().toString().toLowerCase(Locale.ROOT),"Next State - Candidacy","Change Mode",
                "ClosePhase","Insert","Edit","Remove","Querying","Read from file")){
            case 1 -> context.forward();
            case 2 -> {
                int option = Input.chooseOption("Chose Mode",EManagement.getTypes()) - 1;
                if (option == 2){
                    option += Input.chooseOption("With type","InterShip","Project","SelfProposal") - 1;
                }

                EManagement changeManager = EManagement.fromInteger(option);
                context.changeManagementMode(changeManager);
            }
            case 3 -> context.closePhase();
            case 4 -> {
                List<String> info = commands.getInfo(management.getDataClass());
                for (int i = 0,j = 0; i < info.size() ;i++,j++){
                    if (fields.get(j).getName().charAt(0) == '_') j++;

                    String type = Utils.splitString(fields.get(j).getType().getName(),"\\.");
                    data.add(Utils.invokeMethod(type,type + " " + info.get(i),Input.class));
                }
                object = management.factory(data);
                context.insert(object);
            }
            case 5 -> {
                Field entityField = fields.get(0);
                data.add(Utils.invokeMethod(Utils.splitString(entityField.getType().getName(),"\\."),entityField.getName(),Input.class));

                List<String> names = new ArrayList<>();
                for (Field e : fields){
                    names.add(e.getName());
                }

                int option = Input.chooseOption("Witch attribute you want to change", names);

                Field changeField = fields.get(option - 1);
                data.add(Utils.invokeMethod(Utils.splitString(changeField.getType().getName(),"\\."),changeField.getName(),Input.class));

                context.edit(data.get(0),data.get(1),changeField.getName(),management.getDataClass());
            }
            case 6 -> {
                Field entityField = fields.get(0);
                data.add(Utils.invokeMethod(Utils.splitString(entityField.getType().getName(),"\\."),entityField.getName(),Input.class));
                context.remove(data.get(0),management.getDataClass());
            }
            case 7 -> System.out.println(context.querying(management.getDataClass()));
            case 8 -> context.readFromFile(Input.readString("File name: ",true),management.getDataClass());
        }
    }

    public void candidacy(){
        switch (Input.chooseOption("State - " + context.getState().toString().toLowerCase(Locale.ROOT),"Previous State - Configuration","Insert", "Edit",
                "Remove","Querying","" + "List of Students","List of proposals", "Close Phase",
                "Next State - Proposals")) {
            case 1 -> context.back();
            case 2 -> {
                long id = Input.readLong("Student id: ");
                context.insert(new Candidacy(id));
            }
            case 3 -> {
                long id = Input.readLong("Student id: ");
                if(Input.chooseOption("Candidacy","Add","Remove") == 1){
                    System.out.println(context.querying(Proposal.class));
                    String idProposal = Input.readString("With proposal you want to add: ",true);
                    context.edit(id,idProposal,"add",null);
                    return;
                }
                System.out.println(context.querying(Candidacy.class));
                String idProposal = Input.readString("With proposal you want to remove: ",true);
                context.edit(id,idProposal,"remove",null);
            }
            case 4 ->{
                long id = Input.readLong("Student id: ");
                context.remove(id,Candidacy.class);
            }
            case 5 -> System.out.println(context.querying(Candidacy.class));
            case 6 -> System.out.println(context.getListOfStudents());
            case 7 ->{
                List<Integer> filters = Input.chooseMultipleOptions("Candidacy","SelfProposals",
                        "Teacher proposals",
                        "Proposals with candidacy",
                        "Proposals without candidacy");
                System.out.println(context.getFilterList(filters));
            }
            case 8 -> context.closePhase();
            case 9 -> context.forward();
        }
    }

    public void proposals(){
        Input.chooseOption("Proposals","Automatic assignment");
    }
}
