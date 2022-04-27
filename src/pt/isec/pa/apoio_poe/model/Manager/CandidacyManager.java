package pt.isec.pa.apoio_poe.model.Manager;

import pt.isec.pa.apoio_poe.Log;
import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Candidacy;
import pt.isec.pa.apoio_poe.model.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Student;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class CandidacyManager extends Manager<Candidacy> {
    public CandidacyManager(Data data) {
        super(data);
    }

    @Override
    public void readFile(String filePath){
        Set<String> data = new HashSet<>();
        List<Candidacy> items = new ArrayList<>();

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
        items.forEach(this::insert);
    }

    public void automaticAssignment(){
        ProposalManager manager = (ProposalManager) data.getManagement().get(Proposal.class);
        Set<Proposal> proposals = manager.getSpecific(SelfProposal.class);
        Set<Candidacy> candidacies = new HashSet<>();
        for (Proposal proposal : proposals){
            if(proposal.getStudent() != -1){
                candidacies.add(new Candidacy(proposal.getStudent(), Set.of(proposal.getId())));
            }
        }
        candidacies.forEach(this::insert);
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
        if(verifyCandidacy(item)){
            return super.insert(item);
        }
        return false;
    }

    private boolean verifyCandidacy(Candidacy item){
        if (find(item.getStudentId(),Candidacy.class) == null){
            Student student = find(item.getStudentId(),Student.class);
            for (String id : item.getProposals()){
                Proposal proposal = find(id,Proposal.class);
                if(proposal == null){
                    Log.getInstance().addMessage("The proposal of id " + id + " does not exist");
                    return false;
                }
                if (proposal.hasCandidacy()){
                    Log.getInstance().addMessage("The proposal of id " + id + " is already assigned to a candidacy");
                    return false;
                }
            }

            if (student != null){
                student.hasCandidacy(true);
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
            proposal.set_hasCandidacy(true);
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
            proposal.set_hasCandidacy(false);
            Log.getInstance().addMessage("The proposal was remove from the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not remove from the candidacy");
        return false;
    }
}
