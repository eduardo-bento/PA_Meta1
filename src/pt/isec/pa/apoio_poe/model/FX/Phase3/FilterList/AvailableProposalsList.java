package pt.isec.pa.apoio_poe.model.FX.Phase3.FilterList;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;

public class AvailableProposalsList extends ListView<Proposal> {
    private ModelManager model;
    public AvailableProposalsList(ModelManager model) {
        this.model = model;
        registerHandlers();
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void update() {
        this.getItems().clear();
        if (model.getProposalsAvailableList() != null){
            this.getItems().addAll(model.getProposalsAvailableList());
        }
    }
}
