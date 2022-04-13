package pt.isec.pa.apoio_poe.model.Proposals;

public class InterShip extends Proposal{
    private String destiny;
    private String entity;
    private long student;

    public InterShip(String id, String title, String destiny, String entity, long student) {
        super(id, title);
        this.destiny = destiny;
        this.entity = entity;
        this.student = student;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        if (destiny.equals("RAS") || destiny.equals("DA") || destiny.equals("SI")){
            this.destiny = destiny;
        }
    }

    @Override
    public String toString() {
        return "InterShip: " +
                "destiny: " + destiny + "\n" +
                "id: " + id + "\n" +
                "title: " + title + "\n";
    }
}
