package pt.isec.pa.apoio_poe.model.Data.FinalProposalManager;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.model.Data.Command.Invoker;
import pt.isec.pa.apoio_poe.model.Data.FinalProposal.FinalProposal;

import java.util.List;

public class FinalProposalManagerReceiver {
    private final FinalProposalManager receiver;
    private final Invoker invokerProposal;
    private final Invoker invokerTeacher;

    public FinalProposalManagerReceiver(Data data) {
        this.receiver = new FinalProposalManager(data);
        invokerProposal = new Invoker();
        invokerTeacher = new Invoker();
    }


    public boolean insert(FinalProposal item) {
       return receiver.insert(item);
    }

    public void automaticAssignmentForProjectAndInterShip(){
        receiver.automaticAssignmentForProjectAndInterShip();
    }

    public boolean automaticAttribution(){
        return receiver.automaticAttribution();
    }

    public boolean tieHandleConflict(long studentId, String proposalId){
        return receiver.tieHandleConflict(studentId,proposalId);
    }

    public String getTieBreakerList(){
       return receiver.getTieBreakerList();
    }

    public void manualAttribution(String proposalID,long studentID) {
       invokerProposal.invokeCommand(new AddProposal(receiver,proposalID,studentID));
    }

    public boolean undoProposal(){
        return invokerProposal.undo();
    }

    public boolean redoProposal() {
        return invokerProposal.redo();
    }

    public boolean manuelRemove(String proposalID){
        return invokerProposal.invokeCommand(new RemoveProposal(receiver,proposalID));
    }

    public void automaticTeacherAttribution(){
       receiver.automaticTeacherAttribution();
    }

    public boolean manualTeacherAttribution(String proposalID, String teacherID) {
        return invokerTeacher.invokeCommand(new AddTeacher(receiver,proposalID,teacherID));
    }

    public FinalProposal getProposal(String proposalId){
        return receiver.getProposal(proposalId);
    }

    public boolean undoTeacher(){
        return invokerTeacher.undo();
    }

    public boolean redoTeacher(){
        return invokerTeacher.redo();
    }

    public boolean manualTeacherRemove(String proposalID){
       return invokerTeacher.invokeCommand(new RemoveTeacher(receiver,proposalID));
    }

    public List<FinalProposal> getFinalProposalWithTeacherList(){
        return receiver.getFinalProposalWithTeacherList();
    }

    public String getFinalProposalWithoutTeacher(){
        return receiver.getFinalProposalWithoutTeacher();
    }

    public List<FinalProposal> getFinalProposalWithoutTeacherList() {
        return receiver.getFinalProposalWithoutTeacherList();
    }

    public String finalProposals(){
        return receiver.finalProposals();
    }

    public List<FinalProposal> getFinalProposalsList(){
        return receiver.getFinalProposalsList();
    }
}
