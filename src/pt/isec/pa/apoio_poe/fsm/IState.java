package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

import java.util.List;

public interface IState {
    boolean insert(Object item);
    <T,K,A> boolean edit(T id, K value, String label, Class<A> type);
    <T> boolean remove(T id);
    List<Object> querying();
    void forward();
    boolean back();

    boolean undo();
    boolean redo();

    List<Student> getStudents();
    List<Teacher> getTeachers();

    List<Student> getStudentsListWithoutCandidacy();
    List<Student> getStudentsListWithCandidacy();
    List<Student> getStudentsListNoProposal();

    List<Proposal> getProposalsWithCandidacyList();
    List<Proposal>  getProjectsList();
    List<Proposal> getProposalsWithoutCandidacyList();
    List<Proposal> getSelfProposalList();

    int getNumberDestiny(String type);
    List<Integer> getPercentage();
    List<Teacher> top5();
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
    String getTeacherList();

    String getData();
    boolean handleConflict(long studentId,String proposalId);

    boolean readFromFile(String filePath);

    EState getState();

    void manualAttribution(String proposal_id, long student_id);
}
