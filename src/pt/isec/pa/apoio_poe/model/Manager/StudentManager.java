package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;

import java.util.*;

public class StudentManager extends Manager<Student> {
    public StudentManager(Data data) {
        super(data);
    }

    public String getListOfStudents(Set<Proposal> proposals){
        List<Long> withCandidacy = new ArrayList<>();
        List<Long> withoutCandidacy = new ArrayList<>();
        List<Long> selfProposal = new ArrayList<>();

        proposals.forEach(proposal -> {if (proposal instanceof SelfProposal){
            selfProposal.add(((SelfProposal) proposal).getStudent());}
        });

        for (Student student : list){
            if (student.hasCandicy()){
                withCandidacy.add(student.getId());
            } else{
                withoutCandidacy.add(student.getId());
            }
        }
        return "With candidacy" + "\n" + withCandidacy + "\n" + "Without candidacy" + "\n" + withoutCandidacy + "Self Proposal" + "\n" + selfProposal;
    }
}
