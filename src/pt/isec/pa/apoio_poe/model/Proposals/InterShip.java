package pt.isec.pa.apoio_poe.model.Proposals;



public class InterShip extends Proposal{
    private String entity;
    private String destiny;
    public InterShip(String id, String title,long student,String destiny, String entity) {
        super(id, title,student);
        this.destiny = destiny;
        this.entity = entity;

    }

    public static InterShip getFakeInterShip(String id, String title,long student,String destiny, String entity){
        return new InterShip(id,title,student,destiny,entity);
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
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
