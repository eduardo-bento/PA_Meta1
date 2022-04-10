package pt.isec.pa.apoio_poe.text;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.utils.Input;
import pt.isec.pa.apoio_poe.utils.Utils;

import java.lang.reflect.Array;
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
                case CANDIDACY -> candicy();
            }
            System.out.println(Log.getInstance().toString());
        }

    }

    public void configuration(){
        if(Input.chooseOption("State - " + context.getState(),"goCandicy","Insert,Edit,remove") == 1){
            context.goCandidacy();
            return;
        }

        EManagement management = EManagement.fromInteger(Input.chooseOption("Chose Mode",EManagement.getTypes()) - 1);
        context.changeManagementMode(management);
        Object object;
        List<Object> data = new ArrayList<>();

        Field[] myClass = management.getDataClass().getDeclaredFields();
        Field[] superClassFields = management.getDataClass().getSuperclass().getDeclaredFields();
        List<Field> fields = new ArrayList<>(myClass.length + superClassFields.length);
        Collections.addAll(fields,superClassFields);
        Collections.addAll(fields,myClass);

        switch (Input.chooseOption(Utils.capitalString(management.name()), "Insert", "Edit", "Remove","Querying")) {
            case 1 -> {
                for (Field f : fields){
                    String type = Utils.splitString(f.getType().getName(),".");
                    data.add(Utils.invokeMethod(type,type + " - " + f.getName(),Input.class));
                }
                object = management.factory(data);
                context.insert(object,management.getDataClass());
            }
            case 2 -> {
                Field entityField = fields.get(0);
                data.add(Utils.invokeMethod(Utils.splitString(entityField.getType().getName(),"."),entityField.getName(),Input.class));

                List<String> names = new ArrayList<>();
                for (Field e : fields){
                    names.add(e.getName());
                }

                int option = Input.chooseOption("Witch attribute you want to change", names);

                Field changeField = fields.get(option - 1);
                data.add(Utils.invokeMethod(Utils.splitString(changeField.getType().getName(),"."),changeField.getName(),Input.class));

                context.edit(data.get(0),data.get(1),changeField.getName(),management.getDataClass());
            }
            case 3 -> {
                Field entityField = fields.get(0);
                data.add(Utils.invokeMethod(Utils.splitString(entityField.getType().getName(),"."),entityField.getName(),Input.class));
                context.remove(data.get(0),management.getDataClass());
            }
            case 4 ->{
                System.out.println(context.querying(management.getDataClass()));
            }
        }
    }

    public void candicy(){
        if(Input.readBoolean("go back to configuration")){
            context.backConfiguration();
        }
    }
}
