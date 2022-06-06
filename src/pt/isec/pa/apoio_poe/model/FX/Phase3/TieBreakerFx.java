package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class TieBreakerFx extends BorderPane {
    ModelManager model;

    public TieBreakerFx(ModelManager model) {
        this.model = model;
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.TIEBREAKER);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void createViews() {

    }
}
