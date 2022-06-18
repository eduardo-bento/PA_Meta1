package pt.isec.pa.apoio_poe.fsm;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

import java.io.Serializable;
import java.util.List;

public abstract class ContextAdapter implements IState, Serializable {
    protected Context context;
    protected Data data;

    public ContextAdapter(Context context, Data data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public boolean undo() {
        return false;
    }

    @Override
    public boolean redo() {
        return false;
    }

    @Override
    public int getNumberDestiny(String type) {
        return 0;
    }

    @Override
    public List<Integer> getPercentage() {
        return null;
    }

    public void changeState(EState state){
        context.changeState(state.stateFactory(context,data));
    }

    @Override
    public void goToMode(int option) {
    }

    @Override
    public List<Teacher> top5() {
        return null;
    }

    @Override
    public String getTeacherList() {
        return null;
    }

    @Override
    public boolean closePhase() {
        return false;
    }

    @Override
    public boolean insert(Object item) {
        return data.insert(item);
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
    public List<Object> querying() {
        return null;
    }

    @Override
    public void forward() {}

    @Override
    public void automaticAttributionWithoutAssociation() {}

    @Override
    public void automaticAssignmentForProjectAndInterShip() {}

    @Override
    public void manualTeacherAttribution(String proposalID, String teacherID) {}

    @Override
    public void manualRemove(String proposalID) {}

    @Override
    public void manualAttribution(String proposal_id, long student_id) {

    }

    @Override
    public boolean handleConflict(long studentId, String proposalId) {
        return false;
    }

    @Override
    public boolean readFromFile(String filePath) {return false;}

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
    public boolean manualTeacherRemove(String proposalID) {
        return false;
    }

    @Override
    public String getListOfStudents() {
        return null;
    }

    @Override
    public void exportFile(String filePath) {}

    @Override
    public List<Student> getStudents() {
        return null;
    }

    @Override
    public List<Teacher> getTeachers() {
        return null;
    }

    @Override
    public List<Student> getStudentsListNoProposal() {
        return null;
    }

    @Override
    public List<Student> getStudentsListWithCandidacy() {
        return null;
    }

    @Override
    public List<Student> getStudentsListWithoutCandidacy() {
        return null;
    }

    @Override
    public List<Proposal> getProjectsList() {
        return null;
    }

    @Override
    public List<Proposal> getProposalsWithCandidacyList() {
        return null;
    }

    @Override
    public List<Proposal> getSelfProposalList() {
        return null;
    }

    @Override
    public List<Proposal> getProposalsWithoutCandidacyList() {
        return null;
    }

    @Override
    public String getFilterList(List<Integer> filters) {
        return null;
    }
}
