package pt.isec.pa.apoio_poe.fsm;

import java.util.List;

public interface IState {
    boolean insert(Object item);
    <T,K,A> boolean edit(T id, K value, String label, Class<A> type);
    <T> boolean remove(T id);
    String querying();
    void forward();
    boolean back();

    void changeMode(EState management);
    EState getMode();
    void goToMode();
    boolean closePhase();

    String getListOfStudents();
    String getFilterList(List<Integer> filters);

    void automaticAttributionWithoutAssociation();
    void automaticAttributionForProposalsWithStudent();

    void readFromFile(String filePath);

    EState getState();
}
