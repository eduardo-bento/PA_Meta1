package pt.isec.pa.apoio_poe.data.Proposals;

import pt.isec.pa.apoio_poe.data.Teacher;

public class Project extends Proposal{
    private String destiny;
    private String teacher;
    private long student;

    public Project(String id, String title, String destiny, String teacher, long student) {
        super(id, title);
        this.destiny = destiny;
        this.teacher = teacher;
        this.student = student;
    }

    public void setDestiny(String destiny) {
        if (destiny.equals("RAS") || destiny.equals("DA") || destiny.equals("SI")){
            this.destiny = destiny;
        }
    }
}
