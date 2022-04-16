package pt.isec.pa.apoio_poe.model.Proposals;

public class Proposal {
    protected String id;
    protected String title;
    private int _hasCandidacy;

    protected Proposal(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Proposal getFakeProposal(String id){
        return new Proposal(id,"---");
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

    public int get_hasCandidacy() {
        return _hasCandidacy;
    }

    public void addCandidacy() {
        _hasCandidacy++;
    }

    public void subCandidacy() {
        _hasCandidacy--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Proposal proposal = (Proposal) o;

        return id != null ? id.equals(proposal.id) : proposal.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
