package pt.isec.pa.apoio_poe.model.Proposals;

public abstract class Proposal {
    protected String id;
    protected String title;
    private int _hasCandidacy;

    public Proposal(String id, String title) {
        this.id = id;
        this.title = title;
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

    public void addCandicy() {
        _hasCandidacy++;
    }

    public void subCandicy() {
        _hasCandidacy--;
    }
}
