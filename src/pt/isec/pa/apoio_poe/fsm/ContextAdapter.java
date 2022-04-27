package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

import java.io.Serializable;
import java.util.List;

public abstract class ContextAdapter implements IState, Serializable {
    protected Context context;
    protected Data data;

    public ContextAdapter(Context context, Data data) {
        this.context = context;
        this.data = data;
    }

    public void changeState(EState state){
        context.changeState(state.stateFactory(context,data));
    }

    @Override
    public void goToMode(int option) {
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
    public void automaticAttributionWithoutAssociation() {}

    @Override
    public void automaticAttributionForProposalsWithStudent() {}

    @Override
    public void manualAttribution(String proposalID, long studentID) {}

    @Override
    public void manualRemove(String proposalID) {}

    @Override
    public void readFromFile(String filePath) {}

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public String getAttributionTeacherData() {
        return null;
    }

    @Override
    public void automaticTeacherAttribution() {}

    @Override
    public String getData() {
        return null;
    }

    @Override
    public void attributeATeacher(String proposalID, String teacherID) {}

    @Override
    public String getListOfStudents() {
        return null;
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return null;
    }
}
