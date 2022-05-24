package pt.isec.pa.apoio_poe.model.Data;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.Data.Proposals.SelfProposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Log;

import java.io.File;
import java.io.FileNotFoundException;
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
        }  catch (FileNotFoundException e){
            Log.getInstance().addMessage("The file does not exist");
        } catch (NoSuchElementException e){
            Log.getInstance().addMessage("Error reading");
        }
        items.forEach(this::insert);
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
        if(verify(item)){
            return super.insert(item);
        }
        return false;
    }

    private boolean verify(Candidacy item){
        if(studentIsAssignedToASelfProposalOrProject(item.getStudentId())){
            return false;
        }
        if (find(item.getStudentId(),Candidacy.class) != null){
            return false;
        }

        if (item.getProposals().isEmpty()){
            Log.getInstance().addMessage("There are no proposals");
            return false;
        }

        for (String id : item.getProposals()){
            Proposal proposal = find(id,Proposal.class);
            if(proposal == null){
                Log.getInstance().addMessage("The proposal of id " + id + " does not exist");
                return false;
            }
            if (proposal.getStudent() != -1){
                return false;
            }
        }

        Student student = find(item.getStudentId(),Student.class);
        if (student != null){
            for (String id : item.getProposals()) {
                Proposal proposal = find(id, Proposal.class);
                proposal.setCandidacy(true);
            }
            student.setCandidacy(true);
            return true;
        }
        return false;
    }

    private boolean studentIsAssignedToASelfProposalOrProject(long studentID){
        if (studentID == -1) return false;
        List<Proposal> proposals = data.getList(Proposal.class);
        for (Proposal proposal : proposals){
            if (proposal instanceof Project || proposal instanceof SelfProposal){
                if (studentID == proposal.getStudent()){
                    Log.getInstance().addMessage("The student is already assigned to a InterShip or a Project");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addProposal(long id,String idProposal){
        Candidacy candidacy = find(id,Candidacy.class);
        if (candidacy != null && candidacy.addProposal(idProposal)){
            Proposal proposal = find(idProposal,Proposal.class);
            if (proposal == null) return false;
            proposal.setCandidacy(true);
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
            proposal.setCandidacy(false);
            Log.getInstance().addMessage("The proposal was remove from the candidacy");
            return true;
        }
        Log.getInstance().addMessage("The proposal was not remove from the candidacy");
        return false;
    }
}
