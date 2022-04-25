package pt.isec.pa.apoio_poe.text;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.fsm.states.TeacherAttributionState;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.utils.Input;

import java.util.List;

public class UserInterface {
    private final Context context;
    private boolean isRunning;

    public UserInterface(Context context) {
        this.context = context;
    }

    public void start() {
        while (true) {
            Log.getInstance().reset();
            switch (context.getState()) {
                case CONFIGURATION_PHASE -> configuration();
                case CONFIGURATION_PHASE_LOCK -> configurationLockPhase();
                case CANDIDACY -> candidacy();
                case CANDIDACY_PHASE_LOCK -> candidacyLockPhase();
                case PROPOSALS_PHASE -> proposals();
                case TEACHER_ATTRIBUTION_PHASE -> teacher();
                case QUERYING_PHASE -> querying();
                default -> mode();
            }
            System.out.println(Log.getInstance().toString());
        }
    }

    private void configuration() {
        switch (Input.chooseOption("Configuration State\n", "Change Mode", "Go to mode: " + context.getMode(), "querying", "Read cvs", "Close phase", "Go to next state","save","load")) {
            case 1 -> context.changeMode(EState.getMode(Input.chooseOption("Modes: ", "Student", "Teacher", "Project", "SelfProposal", "InterShip")));
            case 2 -> context.goToMode();
            case 3 -> System.out.println(context.querying());
            case 4 -> context.readFromFile(Input.readString("File Path: ", true));
            case 5 -> context.closePhase();
            case 6 -> context.forward();
            case 7 -> context.save();
            case 8 -> context.load();
        }
    }

    private void configurationLockPhase() {
        switch (Input.chooseOption("Configuration State\n", "Change Mode", "Querying", "Go to next state")) {
            case 1 -> context.changeMode(EState.getMode(Input.chooseOption("Modes: ", "Student", "Teacher", "Project", "SelfProposal", "InterShip")));
            case 2 -> System.out.println(context.querying());
            case 3 -> context.forward();
        }
    }

    private void mode() {
        switch (Input.chooseOption(context.getState() + " mode\n", "Back to configuration", "Insert", "Remove")) {
            case 1 -> context.back();
            case 2 -> context.insert(Input.readClass(context.getState()));
            case 3 -> context.remove(null);
        }
    }

    private void candidacy() {
        switch (Input.chooseOption("Candidacy State\n", "Back to configuration", "Insert", "Remove", "Querying", "Read CVS", "List of Students",
                "List of proposals", "Close phase", "Next state - Proposals")) {
            case 1 -> context.back();
            case 2 -> context.insert(Input.readClass(context.getState()));
            case 3 -> context.remove(new Candidacy(Input.readLong("Student id: ")));
            case 4 -> System.out.println(context.querying());
            case 5 -> context.readFromFile(Input.readString("File path: ", true));
            case 6 -> System.out.println(context.getListOfStudents());
            case 7 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Candidacy", "SelfProposals",
                        "Teacher proposals",
                        "Proposals with candidacy",
                        "Proposals without candidacy");
                System.out.println(context.getFilterList(filters));
            }
            case 8 -> context.closePhase();
            case 9 -> context.forward();
        }
    }

    private void candidacyLockPhase() {
        switch (Input.chooseOption("Candidacy State\n", "Back state", "Querying", "List of Students",
                "List of proposals", "Go to next state")) {
            case 1 -> context.back();
            case 2 -> System.out.println(context.querying());
            case 3 -> System.out.println(context.getListOfStudents());
            case 4 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Candidacy", "SelfProposals",
                        "Teacher proposals",
                        "Proposals with candidacy",
                        "Proposals without candidacy");
                System.out.println(context.getFilterList(filters));
            }
            case 5 -> context.forward();
        }
    }

    private void proposals() {
        switch (Input.chooseOption("Proposals", "Automatic attribution for proposals with student",
                "Automatic attribution for students without defined attribution",
                "List of students", "List of proposals", "Manual attribution", "Manual Remove", "Close phase", "Next phase")) {
            case 1 -> context.automaticAttributionForProposalsWithStudent();
            case 2 -> context.automaticAttribution();
            case 3 -> System.out.println(context.getListOfStudents());
            case 4 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Candidacy", "SelfProposals",
                        "Teacher proposals",
                        "Available proposals",
                        "Proposals already attributed");
                System.out.println(context.getFilterList(filters));
            }
            case 5 -> context.manualAttribution(Input.readString("Proposal id", true), Input.readLong("Student id"));
            case 6 -> context.manualRemove(Input.readString("Proposal id:", true));
            case 7 -> context.closePhase();
            case 8 -> context.forward();
        }
    }

    private void teacher() {
        switch (Input.chooseOption("Teacher Attribution Phase", "Automatic attribution", "Data", "Close Phase", "Back","Next State")) {
            case 1 -> context.automaticTeacherAttribution();
            case 2 -> System.out.println(context.getAttributionTeacherData());
            case 3 -> context.closePhase();
            case 4 -> context.back();
            case 5 -> context.forward();
        }
    }

    private void querying() {
        switch (Input.chooseOption("Querying Phase", "Data", "Exit")) {
            case 1 -> System.out.println(context.getData());
            case 2 -> isRunning = false;
        }
    }
}
