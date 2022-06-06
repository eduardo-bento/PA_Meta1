package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.ListPane;
import pt.isec.pa.apoio_poe.model.FX.Phase2.ListStudents;

public class ProposalFx extends BorderPane {
    ModelManager model;

    public ProposalFx(ModelManager model) {
        this.model = model;
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSALS_PHASE);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void createViews() {

    }
}
