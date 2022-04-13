package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

public class ProposalState extends ContextAdapter{
    public ProposalState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public EState getState() {
        return EState.PROPOSALS;
    }
}
