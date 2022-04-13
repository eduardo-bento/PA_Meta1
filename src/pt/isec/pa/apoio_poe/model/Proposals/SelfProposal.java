package pt.isec.pa.apoio_poe.model.Proposals;

public class SelfProposal extends Proposal {
    private long student;

    public SelfProposal(String id, String title, long student) {
        super(id, title);
        this.student = student;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "SelfProposal: " +
                "id: " + id + "\n" +
                "title: " + title + "\n";
    }
}
