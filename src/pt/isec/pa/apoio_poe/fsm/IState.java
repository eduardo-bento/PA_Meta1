package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.EManagement;

import java.util.List;

public interface IState {
    boolean insert(Object object);
    <T,K,A> boolean edit(T entity, K value, String label, Class<A> typeClass);
    <T,K> boolean remove(T id,Class<K> typeClass);
    <T> String querying(Class<T> typeClass);
    void forward();
    boolean back();

    void changeManagementMode(EManagement management);
    EManagement getManagementMode();
    boolean closePhase();

    String getListOfStudents();
    String getFilterList(List<Integer> filters);

    void automaticAssignment();

    EState getState();
}
