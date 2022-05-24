package pt.isec.pa.apoio_poe.model.Data.Proposals;

public class Project extends Proposal{
    private String teacher;
    private String destiny;

    public Project(String id, String title,long student,String destiny, String teacher) {
        super(id, title,student);
        this.destiny = destiny;
        this.teacher = teacher;
    }

    public static Project getFakeProject(String id, String title, long student, String destiny, String teacher){
        return new Project(id,title,student,destiny,teacher);
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

    public void setDestiny(String destiny) {
        if (destiny.equals("RAS") || destiny.equals("DA") || destiny.equals("SI")){
            this.destiny = destiny;
        }
    }

    @Override
    public String toString() {
        return "Project:\n" +
                super.toString() +
                "destiny: " + destiny + "\n" +
                "-".repeat(20) + "\n";
    }
}
