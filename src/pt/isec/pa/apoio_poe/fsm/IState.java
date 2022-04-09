package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.EManagement;

public interface IState {
    boolean insert(Object object);
    <T,K> boolean edit(T entity,K value, String label);

    <T> boolean remove(T id);
    void changeManagementMode(EManagement management);
    void closePhase();

    void goCandidacy();
    boolean backConfiguration();

    String getListOfStudents();
    String getListOfProjects_Stages();

    EState getState();
}
