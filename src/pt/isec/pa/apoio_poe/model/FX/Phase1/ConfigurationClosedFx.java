package pt.isec.pa.apoio_poe.model.FX.Phase1;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

public class ConfigurationClosedFx extends BorderPane {
    ModelManager model;
    MyButton next,changeMode;
    ChoiceBox<String> types;

    public ConfigurationClosedFx(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        next = new MyButton("Next state");
        types = new ChoiceBox(FXCollections.observableArrayList(
                "Student", "Teacher", "Proposal")
        );
        types.setValue("Student");
        types.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        changeMode = new MyButton("Change Mode");

        VBox vBox = new VBox(next, types,changeMode);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        setCenter(vBox);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> {
            update();
        });

        next.setOnAction(next -> {
            model.forward();
        });

        changeMode.setOnAction(changeMode -> {
            switch (types.getValue()){
                case "Student" -> model.goToMode(1);
                case "Teacher" -> model.goToMode(2);
                case "Proposal" -> model.goToMode(3);
            }
        });
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CONFIGURATION_PHASE_LOCK);
    }
}
