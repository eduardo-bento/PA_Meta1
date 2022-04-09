package pt.isec.pa.apoio_poe.data.Proposals;

public abstract class Proposal {
    private String id;
    private String title;

    public Proposal(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
