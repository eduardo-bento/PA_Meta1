package pt.isec.pa.apoio_poe.fsm.states.phase2;

import pt.isec.pa.apoio_poe.model.Log;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.data.Data;

import java.util.List;

public class CandidacyState extends ContextAdapter {
    public CandidacyState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public boolean back() {
        if (!data.isPhaseLock(0)){
            changeState(EState.CONFIGURATION_PHASE);
            return true;
        }
        changeState(EState.CONFIGURATION_PHASE_LOCK);
        return true;
    }

    @Override
    public void forward() {
        if (!data.isPhaseLock(2)){
            if (!data.isPhaseLock(1)){
                changeState(EState.PROPOSAL_PHASE_SINGLE);
                return;
            }
            changeState(EState.PROPOSALS_PHASE);
            return;
        }
        changeState(EState.PROPOSALS_PHASE_LOCK);
    }

    @Override
    public boolean closePhase() {
        if (data.isPhaseLock(0)){
            data.lockPhase(1);
            Log.getInstance().addMessage("The Candidacy phase is now locked");
            forward();
            return true;
        }
        Log.getInstance().addMessage("The Candidacy phase could not be locked " +
                "because the configuration phase is not lock");
        return false;
    }

    @Override
    public void readFromFile(String filePath) {
        data.readCSV(filePath,Candidacy.class);
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
