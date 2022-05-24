package pt.isec.pa.apoio_poe.model.Data.Proposals;

public class SelfProposal extends Proposal {
    public SelfProposal(String id, String title, long student) {
        super(id, title,student);
    }

    public static SelfProposal getFakeSelfProposal(String id, String title, long student){
        return new SelfProposal(id,title,student);
    }

    @Override
    public String toString() {
        return "SelfProposal:\n" +
                super.toString() +
                "-".repeat(20) + "\n";
    }
}
