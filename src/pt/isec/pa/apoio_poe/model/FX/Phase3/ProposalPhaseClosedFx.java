package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

import java.util.Collections;

public class ProposalPhaseClosedFx extends BorderPane {
    ModelManager model;
    MyButton exportButton,next,previous;
    StudentsList studentsList;
    FilterProposal filterProposal;
    TextField exportField;

    public ProposalPhaseClosedFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSALS_PHASE_LOCK);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());

        previous.setOnAction(event -> {
            model.back();
        });

        next.setOnAction(event -> {
            model.forward();
        });

        exportButton.setOnAction(event -> {
            if (exportField.getText().isBlank()){
                exportField.setStyle("-fx-background-color: #FF0000;");
                return;
            }
            exportField.setStyle(null);
            model.exportFile(exportField.getText());
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        studentsList = new StudentsList(model);
        filterProposal = new FilterProposal(model);

        studentsList.setPadding(new Insets(10));

        next = new MyButton("Next");
        previous = new MyButton("Back");

        exportButton = new MyButton("export");
        exportField = new TextField();
        exportField.setPromptText("File path");

        VBox export = new VBox(exportButton,exportField);
        export.setAlignment(Pos.CENTER);
        export.setSpacing(10);

        HBox center = new HBox(filterProposal,studentsList,export);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(10);

        setCenter(center);

        VBox right = new VBox(previous,next);
        right.setSpacing(10);

        setRight(right);
    }
}
