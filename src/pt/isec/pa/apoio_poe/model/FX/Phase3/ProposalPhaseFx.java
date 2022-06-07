package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

public class ProposalPhaseFx extends BorderPane {
    ModelManager model;
    MyButton previous,next,closePhase, automaticAttributionForSelfProposalAndProject,
    automaticAttributionForStudentsWithoutDefinedAttribution,
            manualAttribution,manualRemove;
    TextField proposalId,studentId;
    TextField proposalId_Remove;
    Label listOfStudents;

    public ProposalPhaseFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSALS_PHASE);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

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
    }

    private void createViews() {
        previous = new MyButton("previous");
        next = new MyButton("next");
        closePhase = new MyButton("Close Phase");
        automaticAttributionForSelfProposalAndProject = new MyButton("Automatic Attribution");
        automaticAttributionForStudentsWithoutDefinedAttribution = new MyButton("Automatic Attribution For Students Without Defined Attribution");

        proposalId = new TextField();
        studentId = new TextField();
        proposalId_Remove = new TextField();
        manualAttribution = new MyButton("Manual Attribution");
        manualRemove = new MyButton("Manual Remove");


        setRight(new VBox(previous,next,closePhase,new VBox(proposalId,studentId,manualAttribution),new VBox(proposalId_Remove,manualRemove)));
        setCenter(new HBox(automaticAttributionForSelfProposalAndProject,automaticAttributionForStudentsWithoutDefinedAttribution));
    }
}
