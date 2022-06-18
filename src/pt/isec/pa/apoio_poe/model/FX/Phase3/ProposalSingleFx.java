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
    StudentsList studentsList;
    FilterProposal filterProposal;

    public ProposalSingleFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSAL_PHASE_SINGLE);
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
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        previous = new MyButton("Back");
        next = new MyButton("Next");
        automaticAttribution = new MyButton("Automatic Attribution");

        studentsList = new StudentsList(model);
        filterProposal = new FilterProposal(model);

        HBox center = new HBox(studentsList,automaticAttribution,filterProposal);
        center.setSpacing(10);
        center.setAlignment(Pos.CENTER);
        center.setPrefWidth(300);

        setCenter(center);

        VBox right = new VBox(previous,next);
        right.setSpacing(10);

        setRight(right);
    }
}
