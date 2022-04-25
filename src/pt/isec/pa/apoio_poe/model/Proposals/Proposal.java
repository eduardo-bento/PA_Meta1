package pt.isec.pa.apoio_poe.model.Proposals;

import pt.isec.pa.apoio_poe.Log;

import java.io.File;
import java.io.Serializable;
import java.security.SecureRandomParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Proposal implements Serializable {
    protected String id;
    protected String title;
    protected long student;
    private boolean _hasCandidacy;

    protected Proposal(String id, String title,long studentID) {
        this.id = id;
        this.title = title;
        this.student = studentID;
    }

    public static Proposal getFakeProposal(String id){
        return new Proposal(id,"---",9999999);
    }

    public long getStudent() {
        return student;
    }

    public void setStudent(long student) {
        this.student = student;
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

    public boolean hasCandidacy() {
        return _hasCandidacy;
    }

    public void set_hasCandidacy(boolean candidacy){
        this._hasCandidacy = candidacy;
    }

    public static List<Object> readFile(String filePath){
        List<Object> items = new ArrayList<>();

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                switch (input.next()){
                    case "T1" -> {
                        String id = input.next();
                        String destiny = input.next();
                        String title = input.next();
                        String entity = input.next();
                        long studentId = -1;
                        if (input.hasNextLong()){
                            studentId = input.nextLong();
                        }
                        items.add(new InterShip(id,title,studentId,destiny,entity));
                    }
                    case "T2" -> {
                        String id = input.next();
                        String destiny = input.next();
                        String title = input.next();
                        String teacher = input.next();
                        long studentId = -1;
                        if (input.hasNextLong()){
                            studentId = input.nextLong();
                        }
                        items.add(new Project(id,title,studentId,destiny,teacher));
                    }
                    case "T3" -> {
                        String id = input.next();
                        String title = input.next();
                        long studentId = input.nextLong();

                        items.add(new SelfProposal(id,title,studentId));
                    }
                }
            }
        }  catch (Exception e){
            Log.getInstance().addMessage("The file does not exist");
        }
        return items;
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
