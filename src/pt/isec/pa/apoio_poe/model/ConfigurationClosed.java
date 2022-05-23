package pt.isec.pa.apoio_poe.model;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.fsm.EState;

public class ConfigurationClosed extends BorderPane {
    ModelManager model;
    Button next,changeMode;
    ChoiceBox<String> types;

    public ConfigurationClosed(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #FFA080;");

        next = new Button("Next state");
        types = new ChoiceBox(FXCollections.observableArrayList(
                "Student", "Teacher", "Proposal")
        );
        types.setValue("Student");

        changeMode = new Button("Change Mode");

        HBox hBox = new HBox(next, types,changeMode);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        setCenter(hBox);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> {
            update();
        });

        next.setOnAction(next -> {
            System.out.println("here");
        });
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CONFIGURATION_PHASE_LOCK);
    }
}
