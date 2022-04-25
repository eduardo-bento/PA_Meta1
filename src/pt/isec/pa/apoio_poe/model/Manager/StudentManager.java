package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;

import java.io.Serializable;
import java.util.*;

public class StudentManager extends Manager<Student> {
    public StudentManager(Data data) {
        super(data);
    }

    public String getStudentsCandidacy(boolean label){
        StringBuilder stringBuilder = new StringBuilder();
        for (Student student : list){
            if (student.hasCandidacy() && label){
                stringBuilder.append(student).append("\n");
            } else if(!student.hasCandidacy() && !label){
                stringBuilder.append(student).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String getStudentWithSelfProposal(){
        StringBuilder stringBuilder = new StringBuilder();
        Set<Proposal> proposals = data.getSelfProposalSet();
        for (Proposal proposal : proposals){
            Student student = find(proposal.getId(),Student.class);
            if(student != null){
                stringBuilder.append(student).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public int branchCount(String branch){
        int count = 0;
        for (Student item : list){
            if(item.getBranch().equals(branch)) count++;
        }
        return count;
    }
}
