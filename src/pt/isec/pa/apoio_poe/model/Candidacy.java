package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.Log;

import java.io.File;
import java.util.*;

public class Candidacy {
    private long studentId;
    private final Set<String> proposals;
    private String proposal;

    public Candidacy(long studentId) {
        this.studentId = studentId;
        proposals = new HashSet<>();
    }

    public Candidacy(long studentId,Set<String> proposals){
        this.studentId = studentId;
        this.proposals = new HashSet<>();
        this.proposals.addAll(proposals);
    }

    public static Candidacy getFakeCandidacy(long id){
        return new Candidacy(id);
    }

    public boolean addProposal(String proposal){
        return proposals.add(proposal);
    }

    public boolean removeProposal(String proposal){
        return proposals.remove(proposal);
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public Set<String> getProposals() {
        return proposals;
    }

    public static List<Object> readFile(String filePath){
        Set<String> data = new HashSet<>();
        List<Object> items = new ArrayList<>();

        try(Scanner input = new Scanner(new File(filePath))) {
            input.useDelimiter(",\\s*|\r\n|\n");
            input.useLocale(Locale.US);
            while(input.hasNext()){
                long studentID = input.nextLong();
                while(!input.hasNextLong()){
                    if (!input.hasNext()) break;
                    data.add(input.next());
                }
                items.add(new Candidacy(studentID,data));
                data.clear();
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
        Candidacy candidacy = (Candidacy) o;
        return studentId == candidacy.studentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Student id: ").append(studentId).append("\n");
        for (String p : proposals){
            stringBuilder.append(p).append("\n");
        }
        return stringBuilder.toString();
    }
}
