package pt.isec.pa.apoio_poe.model.FX.Phase2.FilterList;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;

public class ProposalWithoutCandidacyList extends ListView<Proposal> {
    private ModelManager model;
    public ProposalWithoutCandidacyList(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        if (model.getProposalsWithoutCandidacyList() != null){
            this.getItems().addAll(model.getProposalsWithoutCandidacyList());
        }
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void update() {
        this.getItems().clear();
        if (model.getProposalsWithoutCandidacyList() != null){
            this.getItems().addAll(model.getProposalsWithoutCandidacyList());
        }
    }
}
