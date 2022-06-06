package pt.isec.pa.apoio_poe.model.FX.Phase1;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

import java.awt.*;

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
        this.setStyle("-fx-background-color: #5F7161;");

        next = new Button("Next state");
        next.setStyle("-fx-background-color: #D0C9C0;");
        closePhase = new Button("Close Phase");
        closePhase.setStyle("-fx-background-color: #D0C9C0;");
        types = new ChoiceBox(FXCollections.observableArrayList(
                "Student", "Teacher", "Proposal")
        );
        types.setValue("Student");
        types.setStyle("-fx-background-color: #D0C9C0;");

        changeMode = new Button("Change Mode");
        changeMode.setStyle("-fx-background-color: #D0C9C0;");
        VBox pane = new VBox(next, types,changeMode,closePhase);
        pane.setSpacing(10);
        pane.setAlignment(Pos.CENTER);
        pane.setPrefWidth(300);
        setCenter(pane);
    }
}
