package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Candidacy;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Project;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Teacher;

import java.util.Arrays;
import java.util.List;

public enum EState {
    CONFIGURATION,
    STUDENT,
    TEACHER,
    PROPOSAL,
    PROPOSALS,
    CANDIDACY,
    ADVISORS,
    CONSULTATION;

    public enum ProposalTypes{
        PROJECT,
        INTER_SHIP,
        SELF_PROPOSAL;

        public static Class<?> getStructureClass(int type){
            return switch (type){
                case 1 -> Project.class;
                case 2 -> InterShip.class;
                default -> SelfProposal.class;
            };
        }
    }

   public IState stateFactory(Context context, Data data){
       return switch (this){
           case CONFIGURATION -> new ConfigurationState(context,data);
           case STUDENT -> new StudentState(context,data);
           case CANDIDACY -> new CandidacyState(context,data);
           case PROPOSALS -> new ProposalState(context,data);
           default -> null;
       };
    }

    public Class<?> getStructureClass(){
        return switch (this){
            case STUDENT -> Student.class;
            case TEACHER -> Teacher.class;
            case CANDIDACY -> Candidacy.class;
            default -> null;
        };
    }

    public Object factory(List<Object> data){
        return switch (this){
            case STUDENT -> createStudent(data);
            case TEACHER ->  new Teacher((String) data.get(0), (String) data.get(1), (Boolean) data.get(2));
            default -> null;
        };
    }

    private Student createStudent(List<Object> data){
        final String errorMsg = "Could not create the student: " + data.get(0) + ":\n   ";
        List<String> branch = Arrays.asList("DA","RAS","SI","DA | SI");
        List<String> curse = Arrays.asList("LEI","LEI-PL");
        boolean classification = (double) data.get(5) > 0 && (double) data.get(5) < 1;

        if (!classification){
            Log.getInstance().addMessage(errorMsg + "because the classification need to be inside the interval [0,1]");
            return null;
        } else if (!branch.contains((String) data.get(4))){
            Log.getInstance().addMessage(errorMsg + "because branch needs to be one of the following types: " + branch);
            return null;
        } else if (!curse.contains((String) data.get(3))){
            Log.getInstance().addMessage(errorMsg + "because curse needs to be one of the following types: " + curse);
            return null;
        }

        return new Student((long) data.get(0),(String) data.get(1),
                (String) data.get(2),(String) data.get(3),
                (String) data.get(4),(double) data.get(5),
                (boolean) data.get(6));
    }

    public static EState getMode(int mode){
        return EState.values()[mode + 1];
    }
}
