package pt.isec.pa.apoio_poe.text;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Candidacy;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Proposal;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.utils.Input;

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
                default -> mode();
            }
            System.out.println(Log.getInstance().toString());
        }
    }

    public void configuration(){
        switch (Input.chooseOption("Configuration State\n","Change Mode","Go to mode: " + context.getMode(),"Go to next state")){
            case 1 -> context.changeMode(EState.getMode(Input.chooseOption("Modes: ","Student","Teacher","Proposal") - 1));
            case 2 -> context.goToMode();
            case 3 -> context.forward();
        }
    }

    public void mode(){
        int type = -1;
        switch (Input.chooseOption(context.getMode() + "mode\n","Back to configuration","Insert","Remove","Querying","Read CVS")){
            case 1 -> context.back();
            case 2 -> {
                if (context.getMode() == EState.PROPOSAL)
                    type = Input.chooseOption("Choose Proposal","Project","InterShip","SelfProposal");
                context.insert(Input.readClass(context.getState(),type));
            }
            case 3 -> context.remove(null);
            case 4 -> System.out.println(context.querying());
            case 5 -> context.readFromFile(Input.readString("File path: ",true));
        }
    }

    public void candidacy(){
        switch (Input.chooseOption("Candidacy State\n","Back to configuration","Insert","Remove","Querying","Read CVS","List of Students",
                "List of proposals","Close phase","Next state - Proposals")){
            case 1 -> context.back();
            case 2 -> context.insert(Input.readClass(context.getState()));
            case 3 -> context.remove(new Candidacy(Input.readLong("Student id: ")));
            case 4 -> System.out.println(context.querying());
            case 5 -> context.readFromFile(Input.readString("File path: ",true));
            case 6 -> System.out.println(context.getListOfStudents());
            case 7 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Candidacy","SelfProposals",
                        "Teacher proposals",
                        "Proposals with candidacy",
                        "Proposals without candidacy");
                System.out.println(context.getFilterList(filters));
            }
            case 8 -> context.closePhase();
            case 10 -> context.forward();
        }
    }

    public void proposals(){
        switch (Input.chooseOption("Proposals","Manual Attribute")){
            case 1 -> context.manualProposalAttribution(Input.readString("Proposal id: ",true),Input.readLong("Student id: "));
        }
    }
}
