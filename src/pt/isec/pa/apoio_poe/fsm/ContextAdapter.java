package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

import java.util.List;

abstract class ContextAdapter implements IState{
    protected Context context;
    protected Data data;

    public ContextAdapter(Context context, Data data) {
        this.context = context;
        this.data = data;
    }

    public void changeState(pt.isec.pa.apoio_poe.fsm.EState state){
        context.changeState(state.stateFactory(context,data));
    }

    @Override
    public void goToMode() {
    }

    @Override
    public boolean closePhase() {
        return false;
    }

    @Override
    public boolean insert(Object item) {
        return false;
    }

    @Override
    public <T,K,A> boolean edit(T id, K value, String label, Class<A> type) {
        return false;
    }

    @Override
    public <T> boolean remove(T id) {
        return false;
    }

    @Override
    public String querying() {
        return null;
    }

    @Override
    public void forward() {}

    @Override
    public void manualProposalAttribution(String proposalID, long studentID) {}

    @Override
    public void manualProposalRemoveAttribution(String proposalID) {}

    @Override
    public void readFromFile(String filePath) {
    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public String getListOfStudents() {
        return null;
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return null;
    }

    @Override
    public void changeMode(EState management){}

    @Override
    public EState getMode() {
        return null;
    }
}
