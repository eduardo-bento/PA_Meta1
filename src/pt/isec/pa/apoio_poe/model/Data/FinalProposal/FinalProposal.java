package pt.isec.pa.apoio_poe.model.Data.FinalProposal;

import java.io.Serializable;

public class FinalProposal implements Serializable {
    private long studentId;
    private String teacher;
    private String proposal;
    private int order;

    public FinalProposal(long studentId, String proposal,int order) {
        this.studentId = studentId;
        this.proposal = proposal;
        teacher = "";
        this.order = order;
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
        return new FinalProposal(studentId,".....",0);
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

    public String exportPhase3(){
        return proposal + "," + order + ",";
    }

    public String exportPhase4(){
        return proposal + "," + order + "," + teacher;
    }

    @Override
    public String toString() {
        return "student id: " + studentId + "\n" +
                "Proposal: " + proposal + "\n" +
                "Order: " + order + "\n";
    }
}
