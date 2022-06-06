package pt.isec.pa.apoio_poe.fsm.states.phase3;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;

import java.util.List;

public class TieBreakerPhase extends ContextAdapter {
    public TieBreakerPhase(Context context, Data data) {
        super(context, data);
    }

    @Override
    public String getData() {
        return data.getTieBreakerList();
    }

    @Override
    public boolean handleConflict(long studentId, String proposalId) {
        if (data.handleConflict(studentId,proposalId)){
            changeState(EState.PROPOSALS_PHASE);
            data.automaticAttribution();
            return true;
        }
        changeState(EState.TIEBREAKER);
        return false;
    }

    @Override
    public List<Object> querying() {
        return super.querying();
    }

    @Override
    public EState getState() {
        return EState.TIEBREAKER;
    }
}
