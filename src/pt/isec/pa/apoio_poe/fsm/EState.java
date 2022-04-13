package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

public enum EState {
    CONFIGURATION,
    CANDIDACY,
    PROPOSALS,
    ADVISORS,
    CONSULTATION;

   public IState factory(Context context,Data data){
       return switch (this){
           case CONFIGURATION -> new ConfigurationState(context,data);
           case CANDIDACY -> new CandidacyState(context,data);
           case PROPOSALS -> new ProposalState(context,data);
           default -> null;
       };
    }
}
