package pt.isec.pa.apoio_poe.model;

import pt.isec.pa.apoio_poe.model.Proposals.Project;

public class FinalProposal {
    private long studentId;
    private String proposal;

    public FinalProposal(long studentId, String proposal) {
        this.studentId = studentId;
        this.proposal = proposal;
    }

    public long getStudentId() {
        return studentId;
    }

    public static FinalProposal getFakeFinalProposal(long studentId){
        return new FinalProposal(studentId,".....");
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinalProposal that = (FinalProposal) o;

        return studentId == that.studentId;
    }

    @Override
    public int hashCode() {
        return (int) (studentId ^ (studentId >>> 32));
    }

    @Override
    public String toString() {
        return "student id: " + studentId + "\n" +
                "Proposal: " + proposal;
    }
}
