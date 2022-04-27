package pt.isec.pa.apoio_poe.model.Candidacy;

import java.io.Serializable;
import java.util.*;

public class Candidacy implements Serializable {
    private long studentId;
    private final List<String> proposals;

    public Candidacy(long studentId) {
        this.studentId = studentId;
        proposals = new ArrayList<>();
    }

    public Candidacy(long studentId,Set<String> proposals){
        this.studentId = studentId;
        this.proposals = new ArrayList<>();
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

    public List<String> getProposals() {
        return proposals;
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

    public String exportCSV(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String proposal : proposals){
            stringBuilder.append(proposal).append(",");
        }
        return stringBuilder.toString();
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
