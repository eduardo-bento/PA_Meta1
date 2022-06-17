package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;

import java.util.List;

public class ProposalState extends ContextAdapter {
    public ProposalState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public boolean readFromFile(String filePath) {
        return data.readCSV(filePath, Proposal.class);
    }

    @Override
    public boolean insert(Object item) {
        return super.insert(item);
    }

    @Override
    public List<Object> querying() {
        return data.querying(Proposal.class);
    }

    @Override
    public boolean back() {
        changeState(EState.CONFIGURATION_PHASE);
        return true;
    }

    @Override
    public <T> boolean remove(T id) {
        return data.remove(id,Proposal.class);
    }

    @Override
    public EState getState() {
        return EState.PROPOSAL;
    }
}
