package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;

import java.util.List;

abstract class ContextAdapter implements IState{
    protected Context context;
    protected Data data;

    public ContextAdapter(Context context, Data data) {
        this.context = context;
        this.data = data;
    }

    public void changeState(EState state){
        context.changeState(state.factory(context,data));
    }

    @Override
    public boolean closePhase() {
        return false;
    }

    @Override
    public boolean insert(Object object) {
        return false;
    }

    @Override
    public <T,K,A> boolean edit(T id, K value, String label, Class<A> type) {
        return false;
    }

    @Override
    public <T,K> boolean remove(T id,Class<K> typeClass) {
        return false;
    }

    @Override
    public <T> String querying(Class<T> typeClass) {
        return null;
    }

    @Override
    public void forward() {

    }

    @Override
    public void readFromFile(String filePath, Class<?> typeClass) {

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
    public void changeManagementMode(EManagement management){}

    @Override
    public EManagement getManagementMode() {
        return null;
    }
}
