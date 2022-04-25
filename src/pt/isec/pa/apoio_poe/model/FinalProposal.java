package pt.isec.pa.apoio_poe.model;

import java.io.Serializable;

public class FinalProposal implements Serializable {
    private long studentId;
    private String teacher;
    private String proposal;

    public FinalProposal(long studentId, String proposal) {
        this.studentId = studentId;
        this.proposal = proposal;
        teacher = "";
    }

    public FinalProposal(long studentId, String proposal,String teacher) {
        this.studentId = studentId;
        this.proposal = proposal;
        this.teacher = teacher;
    }
    public long getStudentId() {
        return studentId;
    }

    public static FinalProposal getFakeFinalproposal(long studentId){
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
