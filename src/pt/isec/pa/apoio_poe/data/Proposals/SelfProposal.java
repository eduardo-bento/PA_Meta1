package pt.isec.pa.apoio_poe.data.Proposals;

public class SelfProposal extends Proposal {
    private long student;

    public SelfProposal(String id, String title, long student) {
        super(id, title);
        this.student = student;
    }
}
