package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.FX.Helper.ListPane;
import pt.isec.pa.apoio_poe.model.Log;


public class ProposalList extends ListPane {
    public ProposalList(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Proposal proposal = (Proposal) this.getSelectionModel().getSelectedItem();
                model.remove(proposal.getId());
            }
        });
    }

    public Proposal getSelected(){
        if (!model.querying().isEmpty() && this.getSelectionModel().getSelectedItem() == null){
            this.setStyle("-fx-background-color: #FF0000;");
            Log.getInstance().addMessage("You need to select one of the teachers to change the name");
            return null;
        }
        this.setStyle(null);
        return (Proposal) this.getSelectionModel().getSelectedItem();
    }
}
