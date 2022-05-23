package pt.isec.pa.apoio_poe.model;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.fsm.EState;

public class CandidacyFx extends BorderPane {
    ModelManager model;
    Button next, changeMode, closePhase;
    ChoiceBox types;

    public CandidacyFx(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CANDIDACY);
    }

    private void createViews() {
        next = new Button("Next state");
        this.setCenter(next);
    }
}
