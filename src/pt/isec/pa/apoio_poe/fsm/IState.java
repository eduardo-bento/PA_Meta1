package pt.isec.pa.apoio_poe.fsm;

import java.util.List;

public interface IState {
    boolean insert(Object item);
    <T,K,A> boolean edit(T id, K value, String label, Class<A> type);
    <T> boolean remove(T id);
    String querying();
    void forward();
    boolean back();

    void goToMode(int option);
    boolean closePhase();

    String getListOfStudents();
    String getFilterList(List<Integer> filters);

    void exportFile(String filePath);
    void automaticAttributionWithoutAssociation();
    void automaticAssignmentForProjectAndInterShip();
    void manualRemove(String proposalID);

    String getAttributionTeacherData();
    void automaticTeacherAttribution();
    void manualTeacherAttribution(String proposalID, String teacherID);
    boolean manualTeacherRemove(String proposalID);

    String getData();
    boolean handleConflict(long studentId,String proposalId);

    void readFromFile(String filePath);

    EState getState();

    void manualAttribution(String proposal_id, long student_id);
}
