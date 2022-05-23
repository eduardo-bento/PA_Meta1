package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.model.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.states.phase1.*;
import pt.isec.pa.apoio_poe.fsm.states.phase3.ProposalAttributionLockState;
import pt.isec.pa.apoio_poe.fsm.states.phase3.ProposalAttributionSingleState;
import pt.isec.pa.apoio_poe.fsm.states.phase3.ProposalAttributionState;
import pt.isec.pa.apoio_poe.fsm.states.phase2.CandidacyState;
import pt.isec.pa.apoio_poe.fsm.states.phase2.CandidacyStateLock;
import pt.isec.pa.apoio_poe.fsm.states.phase3.TieBreakerPhase;
import pt.isec.pa.apoio_poe.fsm.states.phase4.TeacherAttributionState;
import pt.isec.pa.apoio_poe.fsm.states.phase5.Querying;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Student.Student;
import pt.isec.pa.apoio_poe.model.Teacher.Teacher;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum EState implements Serializable {
    CONFIGURATION_PHASE,STUDENT,TEACHER,STUDENT_LOCK,TEACHER_LOCK,PROPOSAL,PROPOSAL_LOCK,CONFIGURATION_PHASE_LOCK,
    PROPOSALS_PHASE,PROPOSALS_PHASE_LOCK,PROPOSAL_PHASE_SINGLE,TIEBREAKER,
    CANDIDACY,CANDIDACY_PHASE_LOCK,
    TEACHER_ATTRIBUTION_PHASE,
    QUERYING_PHASE;

   public IState stateFactory(Context context, Data data){
       return switch (this){
           case CONFIGURATION_PHASE -> new ConfigurationState(context,data);
           case CONFIGURATION_PHASE_LOCK -> new ConfigurationStateLock(context,data);
           case STUDENT -> new StudentState(context,data);
           case TEACHER -> new TeacherState(context,data);
           case PROPOSAL -> new ProposalState(context,data);
           case STUDENT_LOCK -> new StudentLockPhase(context,data);
           case TEACHER_LOCK -> new TeacherLockPhase(context,data);
           case PROPOSAL_LOCK -> new ProposalLockPhase(context,data);
           case CANDIDACY -> new CandidacyState(context,data);
           case CANDIDACY_PHASE_LOCK -> new CandidacyStateLock(context,data);
           case PROPOSALS_PHASE -> new ProposalAttributionState(context,data);
           case PROPOSALS_PHASE_LOCK -> new ProposalAttributionLockState(context,data);
           case PROPOSAL_PHASE_SINGLE -> new ProposalAttributionSingleState(context,data);
           case TIEBREAKER ->  new TieBreakerPhase(context,data);
           case TEACHER_ATTRIBUTION_PHASE -> new TeacherAttributionState(context,data);
           case QUERYING_PHASE -> new Querying(context,data);
       };
    }

    public Class<?> getStructureClass(){
        return switch (this){
            case STUDENT -> Student.class;
            case TEACHER -> Teacher.class;
            case CANDIDACY -> Candidacy.class;
            default -> Proposal.class;
        };
    }

    public Object factory(List<Object> data){
        return switch (this){
            case STUDENT -> createStudent(data);
            case TEACHER ->  new Teacher((String) data.get(0), (String) data.get(1), (Boolean) data.get(2));
            case CANDIDACY -> new Candidacy((Long) data.get(0));
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
        return EState.values()[mode];
    }
}
