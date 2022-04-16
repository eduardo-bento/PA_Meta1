package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.EManagement;
import pt.isec.pa.apoio_poe.data.Data;

import java.util.List;

public class CandidacyState extends ContextAdapter{
    public CandidacyState(Context context, Data data) {
        super(context, data);
    }


    @Override
    public boolean back() {
        if (!data.isPhaseLock(EState.CANDIDACY)){
            data.setCurrentMode(EManagement.STUDENTS);
            changeState(EState.CONFIGURATION);
        }
        return true;
    }

    @Override
    public boolean closePhase() {
        if (data.isPhaseLock(EState.CONFIGURATION)){
            data.lockPhase(EState.CANDIDACY);
            changeState(EState.PROPOSALS);
            return true;
        }
        Log.getInstance().addMessage("Could not close because configuration state is not close");
        return false;
    }

    @Override
    public void forward() {
        changeState(EState.PROPOSALS);
    }

    @Override
    public boolean insert(Object object) {
        return data.insert(object);
    }

    @Override
    public <T> String querying(Class<T> typeClass) {
        return data.querying(typeClass);
    }

    @Override
    public <T, K> boolean remove(T id, Class<K> type) {
        return data.remove(id,type);
    }

    @Override
    public <T, K, A> boolean edit(T id, K value, String label, Class<A> type) {
        return data.edit(id,value,label,type);
    }

    @Override
    public String getListOfStudents() {
        return data.getListOfStudents();
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return data.getListProposals(filters);
    }

    @Override
    public EState getState() {
        return EState.CANDIDACY;
    }
}
