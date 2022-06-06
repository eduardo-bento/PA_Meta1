package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.FX.ListPane;


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
}
