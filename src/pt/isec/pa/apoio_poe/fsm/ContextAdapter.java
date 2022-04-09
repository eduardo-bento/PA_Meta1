package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.data.EManagement;

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
    public void closePhase() {

    }

    @Override
    public boolean insert(Object object) {
        return false;
    }

    @Override
    public <T,K> boolean edit(T entity, K value, String label) {
        return false;
    }

    @Override
    public <T> boolean remove(T id) {
        return false;
    }

    @Override
    public void goCandidacy() {

    }

    @Override
    public boolean backConfiguration() {
        return false;
    }

    @Override
    public String getListOfStudents() {
        return null;
    }

    @Override
    public String getListOfProjects_Stages() {
        return null;
    }

    @Override
    public void changeManagementMode(EManagement management){}
}
