package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Phase2.ListStudents;

import java.util.Collections;

public class ProposalSingleFx extends BorderPane {
    ModelManager model;
    MyButton previous,next,automaticAttribution;
    Label listOfStudents;
    ChoiceBox<String> filter;
    Label filterLabel;

    public ProposalSingleFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSAL_PHASE_SINGLE);
        listOfStudents.setText(model.getListOfStudents());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        previous.setOnAction(event -> {
            model.back();
        });

        next.setOnAction(event -> {
            model.forward();
        });

        automaticAttribution.setOnAction(event -> {
            model.automaticAssignmentForProjectAndInterShip();
        });

        filter.setOnAction(event -> {
            filterLabel.setText(model.getFilterList(Collections.singletonList(filter.getSelectionModel().getSelectedIndex())));
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        previous = new MyButton("Back");
        next = new MyButton("Next");
        automaticAttribution = new MyButton("Automatic Attribution");

        listOfStudents = new Label();
        listOfStudents.setText(model.getListOfStudents());
        listOfStudents.setPadding(new Insets(10));
        listOfStudents.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        filter = new ChoiceBox<>(FXCollections.observableArrayList(
                "Proposals","SelfProposals","Teacher proposals","Available proposals","Proposals already attributed"));
        filter.setValue("Available proposals");
        filter.setStyle("-fx-background-color: #D0C9C0;");
        filterLabel = new Label(model.getFilterList(Collections.singletonList(filter.getSelectionModel().getSelectedIndex())));
        filterLabel.setPadding(new Insets(10));
        filterLabel.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        VBox filters = new VBox(filter,filterLabel);
        filters.setAlignment(Pos.CENTER);
        filters.setSpacing(10);

        HBox center = new HBox(automaticAttribution,listOfStudents,filters);
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.setPrefWidth(300);

        setCenter(center);

        VBox right = new VBox(previous,next);
        right.setSpacing(10);

        setRight(right);
    }
}
