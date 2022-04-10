package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.EManagement;

public interface IState {
    <T> boolean insert(Object object,Class<T> typeClass);
    <T,K,A> boolean edit(T entity, K value, String label, Class<A> typeClass);
    <T,K> boolean remove(T id,Class<K> typeClass);
    <T> String querying(Class<T> typeClass);
    void changeManagementMode(EManagement management);
    void closePhase();

    void goCandidacy();
    boolean backConfiguration();

    String getListOfStudents();
    String getListOfProjects_Stages();

    EState getState();
}
