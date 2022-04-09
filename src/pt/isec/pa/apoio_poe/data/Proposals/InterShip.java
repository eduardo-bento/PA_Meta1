package pt.isec.pa.apoio_poe.data.Proposals;

import pt.isec.pa.apoio_poe.data.Student;

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

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        if (destiny.equals("RAS") || destiny.equals("DA") || destiny.equals("SI")){
            this.destiny = destiny;
        }
    }
}
