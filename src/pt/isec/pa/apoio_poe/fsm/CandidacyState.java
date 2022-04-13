package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Student;

import java.util.List;

public class CandidacyState extends ContextAdapter{
    public CandidacyState(Context context, Data data) {
        super(context, data);
    }


    @Override
    public boolean back() {
        if (!data.isPhaseLock(EState.CANDIDACY)){
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
        Candidacy candicy = (Candidacy) object;
        Student student = data.find(candicy.getStudentId(),Student.class);
        if (student != null){
            student.set_hasCandicy(true);
            return data.insert(object);
        }
        return false;
    }

    @Override
    public <T> String querying(Class<T> typeClass) {
        return data.querying(typeClass);
    }

    @Override
    public <T, K> boolean remove(T id, Class<K> typeClass) {
        Candidacy candidacy = data.find(id,Candidacy.class);
        Student student = data.find(candidacy.getStudentId(),Student.class);
        if (student != null){
            student.set_hasCandicy(false);
            data.remove(id,typeClass);
            return true;
        }
        return false;
    }

    @Override
    public <T, K, A> boolean edit(T entity, K value, String label, Class<A> typeClass) {
        if (label.equals("add")){
            return data.addProposal((long) entity,(String) value);
        }
        return data.removeProposal((long) entity,(String) value);
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
