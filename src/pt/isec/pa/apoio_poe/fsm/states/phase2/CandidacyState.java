package pt.isec.pa.apoio_poe.fsm.states.phase2;

import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.data.Data;

import java.util.List;

public class CandidacyState extends ContextAdapter {
    public CandidacyState(Context context, Data data) {
        super(context, data);
    }


   /* @Override
    public boolean back() {
        if (!data.isPhaseLock(EState.CANDIDACY)){
            changeState(EState.CONFIGURATION_PHASE);
        }
        return true;
    }

    @Override
    public boolean isPhaseLock() {
        return data.isPhaseLock(EState.CANDIDACY);
    }

    @Override
    public boolean closePhase() {
        if (data.isPhaseLock(EState.CONFIGURATION_PHASE)){
            data.lockPhase(EState.CANDIDACY);
            changeState(EState.PROPOSALS_PHASE);
            return true;
        }
        Log.getInstance().addMessage("Could not close because configuration state is not close");
        return false;
    }*/

    @Override
    public void readFromFile(String filePath) {
        data.readCSV(filePath,Candidacy.class);
    }

    @Override
    public void forward() {
        changeState(EState.PROPOSALS_PHASE);
    }

    @Override
    public boolean insert(Object item) {
        return data.insert(item);
    }

    @Override
    public String querying() {
        return data.querying(Candidacy.class);
    }

    @Override
    public <T> boolean remove(T id) {
        return data.remove(id, Candidacy.class);
    }

    @Override
    public <T, K, A> boolean edit(T id, K value, String label, Class<A> type) {
        return data.edit(id,value,label,type);
    }

    @Override
    public String getListOfStudents() {
        return data.getListOfStudents_CandidacyPhase();
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return data.getListProposals_CandidacyPhase(filters);
    }

    @Override
    public EState getState() {
        return EState.CANDIDACY;
    }
}
