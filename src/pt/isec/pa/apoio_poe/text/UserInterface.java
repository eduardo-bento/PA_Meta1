package pt.isec.pa.apoio_poe.text;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.utils.Input;

import java.util.List;

public class UserInterface {
    private final Context context;
    private boolean isRunning = true;

    public UserInterface(Context context) {
        this.context = context;
    }

    public void start() {
        while (isRunning) {
            Log.getInstance().reset();
            switch (context.getState()) {
                case CONFIGURATION_PHASE -> configuration();
                case CONFIGURATION_PHASE_LOCK -> configurationLock();
                case STUDENT,TEACHER,PROPOSAL ->  mode();
                case STUDENT_LOCK,TEACHER_LOCK,PROPOSAL_LOCK -> modeLock();
                case CANDIDACY -> candidacy();
                case CANDIDACY_PHASE_LOCK -> candidacyLockPhase();
                case PROPOSAL_PHASE_SINGLE -> proposalsSingle();
                case PROPOSALS_PHASE -> proposals();
                case PROPOSALS_PHASE_LOCK -> proposalsLock();
                case TIEBREAKER -> tieBreaker();
                case TEACHER_ATTRIBUTION_PHASE -> teacher();
                case QUERYING_PHASE -> querying();
            }
            System.out.println(Log.getInstance().toString());
        }
    }

    private void configuration() {
        switch (Input.chooseOption("Configuration State\n", "Chose mode","Close phase","Next state - Candidacy","save","load")) {
            case 1 -> context.goToMode(Input.chooseOption("Mode: ","Student","Teacher","Proposal"));
            case 2 -> context.closePhase();
            case 3 -> context.forward();
            case 4 -> context.save();
            case 5 -> context.load();
        }
    }

    private void configurationLock() {
        switch (Input.chooseOption("Configuration State\n", "Change Mode", "Go to next state")) {
            case 1 -> context.goToMode(Input.chooseOption("Mode: ","Student","Teacher","Proposal"));
            case 2 -> context.forward();
        }
    }

    private void mode() {
        switch (Input.chooseOption("Mode: ", "Back to configuration", "Read from file", "Querying")) {
            case 1 -> context.back();
            case 2 -> context.readFromFile(Input.readString("file path: ",true));
            case 3 -> System.out.println(context.querying());
        }
    }

    private void modeLock() {
        switch (Input.chooseOption("Mode: ", "Back to configuration", "Querying")) {
            case 1 -> context.back();
            case 2 -> System.out.println(context.querying());
        }
    }

    private void candidacy() {
        switch (Input.chooseOption("Candidacy State\n", "Back to configuration", "Querying", "Read from file", "List of Students",
                "List of proposals", "Close phase", "Next state - Proposals")) {
            case 1 -> context.back();
            case 2 -> System.out.println(context.querying());
            case 3 -> context.readFromFile(Input.readString("file path: ", true));
            case 4 -> System.out.println(context.getListOfStudents());
            case 5 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Candidacy",
                        "SelfProposals",
                        "Teacher proposals",
                        "Proposals with candidacy",
                        "Proposals without candidacy");
                System.out.println(context.getFilterList(filters));
            }
            case 6 -> context.closePhase();
            case 7 -> context.forward();
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

    private void proposalsSingle() {
        switch (Input.chooseOption("Proposals (lock previous phase to have more options)", "Back to candidacy state", "Automatic attribution for SelfProposal and Project",
                "List of students", "List of proposals", "Next phase teacher attribution")) {
            case 1 -> context.back();
            case 2 -> context.automaticAssignmentForProjectAndInterShip();
            case 3 -> System.out.println(context.getListOfStudents());
            case 4 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Proposals", "SelfProposals",
                        "Teacher proposals",
                        "Available proposals",
                        "Proposals already attributed");
                System.out.println(context.getFilterList(filters));
            }
            case 5 -> context.forward();
        }
    }

    private void proposals() {
        switch (Input.chooseOption("Proposals", "Back to candidacy state","Automatic attribution for SelfProposal and Project",
                "Automatic attribution for students without defined attribution",
                "List of students", "List of proposals", "Manual attribution", "Manual Remove", "Close phase","Export file", "Next phase teacher attribution")) {
            case 1 -> context.back();
            case 2 -> context.automaticAssignmentForProjectAndInterShip();
            case 3 -> context.automaticAttribution();
            case 4 -> System.out.println(context.getListOfStudents());
            case 5 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Proposals", "SelfProposals",
                        "Teacher proposals",
                        "Available proposals",
                        "Proposals already attributed");
                System.out.println(context.getFilterList(filters));
            }
            case 6 -> context.manualAttribution(Input.readString("Proposal id", true), Input.readLong("Student id"));
            case 7 -> context.manualRemove(Input.readString("Proposal id:", true));
            case 8 -> context.closePhase();
            case 9 -> context.exportFile(Input.readString("file path: ",true));
            case 10 -> context.forward();
        }
    }

    private void proposalsLock(){
        switch (Input.chooseOption("Proposals", "Back to candidacy state",
                "List of students", "List of proposals","Export file", "Next phase teacher attribution")) {
            case 1 -> context.back();
            case 2 -> System.out.println(context.getListOfStudents());
            case 3 -> {
                List<Integer> filters = Input.chooseMultipleOptions("Proposals", "SelfProposals",
                        "Teacher proposals",
                        "Available proposals",
                        "Proposals already attributed");
                System.out.println(context.getFilterList(filters));
            }
            case 4 -> context.exportFile(Input.readString("file path: ",true));
            case 5 -> context.forward();
        }

    }

    private void tieBreaker(){
        switch (Input.chooseOption("TieBreaker Phase", "Data", "Change")) {
            case 1 -> System.out.println(context.getData());
            case 2 -> context.handleConflict(Input.readLong("Student id: "),Input.readString("Proposal id:",true));
        }
    }

    private void teacher() {
        switch (Input.chooseOption("Teacher Attribution Phase","Back","Data","Automatic attribution","Export to file",
                "Manual Attribution","Manual Remove","Querying","Close Phase")) {
            case 1 -> context.back();
            case 2 -> System.out.println(context.getAttributionTeacherData());
            case 3 -> context.automaticTeacherAttribution();
            case 4 -> context.exportFile(Input.readString("file path: ",true));
            case 5 -> {
                System.out.println(context.getTeacherList());
                context.manualTeacherAttribution(Input.readString("Proposal id",true),Input.readString("Teacher id",true));
            }
            case 6 -> {
                System.out.println(context.getTeacherList());
                context.manualTeacherRemove(Input.readString("Proposal id",true));
            }
            case 7 -> System.out.println(context.querying());
            case 8 -> context.closePhase();
        }
    }

    private void querying() {
        switch (Input.chooseOption("Querying Phase", "Data","Export to file","Exit")) {
            case 1 -> System.out.println(context.getData());
            case 2 -> context.exportFile(Input.readString("file path: ",true));
            case 3 -> isRunning = false;
        }
    }
}
