package pt.isec.pa.apoio_poe.model.Data.Proposals;

import java.io.Serializable;

public class Proposal implements Serializable {
    protected String id;
    protected String title;
    protected long student;
    private boolean _hasCandidacy;
    private boolean assigned;

    protected Proposal(String id, String title,long studentID) {
        this.id = id;
        this.title = title;
        this.student = studentID;
    }

    public static Proposal getFakeProposal(String id){
        return new Proposal(id,"---",9999999);
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasCandidacy() {
        return _hasCandidacy;
    }

    public void setCandidacy(boolean candidacy){
        this._hasCandidacy = candidacy;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposal)) return false;

        Proposal proposal = (Proposal) o;

        return id != null ? id.equals(proposal.id) : proposal.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "-".repeat(20) + "\n" +
                "title: " + title + "\n" +
                (student == -1 ? "no student associated" : "student: " + student) + "\n";
    }
}
