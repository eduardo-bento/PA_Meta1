package pt.isec.pa.apoio_poe.data.Proposals;

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

    public String getDestiny() {
        return destiny;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
    }

    public void setDestiny(String destiny) {
        if (destiny.equals("RAS") || destiny.equals("DA") || destiny.equals("SI")){
            this.destiny = destiny;
        }
    }
}
