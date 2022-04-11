package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;

public class CandicyState extends ContextAdapter{
    public CandicyState(Context context, Data data) {
        super(context, data);
    }


    @Override
    public boolean backConfiguration() {
        changeState(EState.CONFIGURATION);
        return true;
    }

    @Override
    public <T> boolean insert(Object object, Class<T> typeClass) {
        return data.insert(object,typeClass);
    }

    @Override
    public <T> String querying(Class<T> typeClass) {
        return super.querying(typeClass);
    }

    @Override
    public <T, K> boolean remove(T id, Class<K> typeClass) {
        return super.remove(id, typeClass);
    }

    @Override
    public <T, K, A> boolean edit(T entity, K value, String label, Class<A> typeClass) {
        return super.edit(entity, value, label, typeClass);
    }

    @Override
    public String getListOfStudents() {
        return super.getListOfStudents();
    }

    @Override
    public EState getState() {
        return EState.CANDIDACY;
    }
}
