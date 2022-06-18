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
import pt.isec.pa.apoio_poe.model.FX.Phase2.List.StudentsWithCandidacy;
import pt.isec.pa.apoio_poe.model.FX.Phase2.List.StudentsWithoutCandidacy;
import pt.isec.pa.apoio_poe.model.FX.Phase3.List.StudentsSelfProposal;
import pt.isec.pa.apoio_poe.model.FX.Phase3.List.StudentsWithNoProposal;
import pt.isec.pa.apoio_poe.model.FX.Phase4.List;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ProposalPhaseFx extends BorderPane {
    ModelManager model;
    MyButton previous,next,closePhase, automaticAttributionForSelfProposalAndProject,
    automaticAttributionForStudentsWithoutDefinedAttribution,
            manualAttribution,manualRemove,undo,redo;
    StudentsList studentsList;
    FilterProposal filterProposal;
    ChoiceBox<String> filter;

    TextField proposalId,studentId;
    TextField proposalId_Remove;
    Label listOfStudents,filterLabel;

    public ProposalPhaseFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSALS_PHASE);
        listOfStudents.setText(model.getListOfStudents());
        filterLabel.setText(model.getFilterList(Collections.singletonList(filter.getSelectionModel().getSelectedIndex())));
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());

        closePhase.setOnAction(event -> {
            model.closePhase();
        });

        previous.setOnAction(event -> {
            model.back();
        });

        next.setOnAction(event -> {
            model.forward();
        });

        automaticAttributionForSelfProposalAndProject.setOnAction(event -> {
            model.automaticAssignmentForProjectAndInterShip();
        });

        automaticAttributionForStudentsWithoutDefinedAttribution.setOnAction(event -> {
            model.automaticAttribution();
        });

        manualAttribution.setOnAction(event -> {
            Long id = null;
            if (proposalId.getText().isEmpty()){
                proposalId.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                proposalId.setStyle(null);
            }

            try {
                id = Long.parseLong(studentId.getText());
                studentId.setStyle(null);
                model.manualAttribution(proposalId.getText(),id);
            } catch (NumberFormatException e){
                studentId.setStyle("-fx-background-color: #FF0000;");
            }
        });

        undo.setOnAction(event -> {
            model.undo();
        });

        redo.setOnAction(event -> {
            model.redo();
        });

        manualRemove.setOnAction(event -> {
            Long id = null;
            if (proposalId.getText().isEmpty()){
                proposalId.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                proposalId.setStyle(null);
            }
            model.manualRemove(proposalId.getText());
        });

        filter.setOnAction(event -> {
            filterLabel.setText(model.getFilterList(Collections.singletonList(filter.getSelectionModel().getSelectedIndex())));
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        previous = new MyButton("Back");
        next = new MyButton("Next");
        closePhase = new MyButton("Close Phase");
        automaticAttributionForSelfProposalAndProject = new MyButton("Automatic Attribution");
        automaticAttributionForStudentsWithoutDefinedAttribution = new MyButton("Automatic Attribution For Students Without Defined Attribution");
        automaticAttributionForStudentsWithoutDefinedAttribution.setMaxWidth(200);

        studentsList = new StudentsList(model);
        filterProposal = new FilterProposal(model);

        undo = new MyButton("Undo");
        redo = new MyButton("Redo");

        proposalId = new TextField();
        proposalId.setPromptText("Proposal id");
        studentId = new TextField();
        studentId.setPromptText("Student id");

        listOfStudents = new Label();
        listOfStudents.setText(model.getListOfStudents());
        listOfStudents.setPadding(new Insets(10));
        listOfStudents.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        proposalId_Remove = new TextField();
        proposalId_Remove.setPromptText("Proposal id");
        manualAttribution = new MyButton("Add");
        manualRemove = new MyButton("Remove");

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

        HBox undo_redo = new HBox(undo,redo);
        undo_redo.setSpacing(10);
        undo_redo.setAlignment(Pos.CENTER);

        VBox boxManualAttribution = new VBox(manualAttribution,proposalId,studentId);
        boxManualAttribution.setAlignment(Pos.CENTER);
        boxManualAttribution.setSpacing(10);

        VBox boxManualRemove = new VBox(manualRemove,proposalId_Remove);
        boxManualRemove.setAlignment(Pos.CENTER);
        boxManualRemove.setSpacing(10);

        VBox attributions = new VBox(boxManualAttribution,boxManualRemove,undo_redo);
        attributions.setAlignment(Pos.CENTER);
        attributions.setSpacing(30);

        VBox left = new VBox(automaticAttributionForSelfProposalAndProject,automaticAttributionForStudentsWithoutDefinedAttribution);
        left.setSpacing(10);
        left.setAlignment(Pos.CENTER);

        HBox center = new HBox(attributions,filterProposal);
        center.setSpacing(60);
        center.setAlignment(Pos.CENTER);
        center.setPrefWidth(300);

        VBox right = new VBox(previous,next,closePhase);
        right.setSpacing(10);

        HBox box = new HBox(left,center);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);

        setLeft(studentsList);
        setRight(right);
        setCenter(box);
    }
}
