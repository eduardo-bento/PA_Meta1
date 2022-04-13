package pt.isec.pa.apoio_poe.model;

import java.util.*;

public class Candidacy {
    private long studentId;
    private final Set<String> proposals;

    public Candidacy(long studentId) {
        this.studentId = studentId;
        proposals = new HashSet<>();
    }

    public Candidacy getFakeCandidacy(long id){
        return new Candidacy(id);
    }

    public void addProposal(String proposal){
        proposals.add(proposal);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidacy candicy = (Candidacy) o;
        return studentId == candicy.studentId;
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
