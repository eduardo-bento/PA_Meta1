package pt.isec.pa.apoio_poe.model.FX.Phase2;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

import java.util.List;

public class CandidacyClosedFx extends BorderPane {
    ModelManager model;
    ListPane listPane;
    ListStudents listStudents;

    public CandidacyClosedFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CANDIDACY_PHASE_LOCK);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void createViews() {
        listStudents = new ListStudents(model);
        listPane = new ListPane(model);

        setLeft(listPane);
        setCenter(listStudents);
    }
}
