package pt.isec.pa.apoio_poe.model.FX;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ConfigurationFx extends BorderPane {
    ModelManager model;
    Button next, changeMode, closePhase;

    ChoiceBox<String> types;

    public ConfigurationFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        next.setOnAction(next -> model.forward());
        changeMode.setOnAction(changeMode -> {
            switch (types.getValue()){
                case "Student" -> model.goToMode(1);
                case "Teacher" -> model.goToMode(2);
                case "Proposal" -> model.goToMode(3);
            }
        });
        closePhase.setOnAction(closePhase -> {
            model.closePhase();
        });
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CONFIGURATION_PHASE);
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #FFA080;");

        next = new Button("Next state");
        closePhase = new Button("Close Phase");
        types = new ChoiceBox(FXCollections.observableArrayList(
                "Student", "Teacher", "Proposal")
        );
        types.setValue("Student");

        changeMode = new Button("Change Mode");
        HBox hBox = new HBox(next, types,changeMode,closePhase);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        setCenter(hBox);
    }
}
