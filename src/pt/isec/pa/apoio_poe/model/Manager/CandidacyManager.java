package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Candidacy;
import pt.isec.pa.apoio_poe.model.dataStrucutures.Student;

public class CandidacyManager extends Manager<Candidacy> {
    public CandidacyManager(Data data) {
        super(data);
    }

    @Override
    public <Q, K, A> boolean edit(Q id, K value, String label, Class<A> type) {
        if (label.equals("add")){
            return addProposal((long) id,(String) value);
        }
        return removeProposal((long) id,(String) value);
    }

    @Override
    public boolean insert(Candidacy item) {
        if(checkCandidacy(item)){
            return super.insert(item);
        }
        return false;
    }

    private boolean checkCandidacy(Candidacy item){
        if (find(item.getStudentId(),Candidacy.class) != null){
            Student student = find(item.getStudentId(),Student.class);
            if (student != null){
                student.set_hasCandidacy(true);
                return true;
            }
        }
        return false;
    }

    public boolean addProposal(long id,String idProposal){
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.addProposal(idProposal)){
            Proposal proposal = find(idProposal,Proposal.class);
            if (proposal == null) return false;

            proposal.addCandidacy();
            Log.getInstance().addMessage("The proposal was added to the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not added to the candidacy");
        return false;
    }

    public boolean removeProposal(long id, String idProposal) {
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.removeProposal(idProposal)){
            Proposal proposal = find(idProposal,Proposal.class);
            if (proposal == null) return false;

            proposal.subCandidacy();
            Log.getInstance().addMessage("The proposal was remove from the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not remove from the candidacy");
        return false;
    }
}
