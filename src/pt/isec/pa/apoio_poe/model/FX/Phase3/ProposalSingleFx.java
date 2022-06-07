package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Phase2.ListStudents;

public class ProposalSingleFx extends BorderPane {
    ModelManager model;
    MyButton previous,next,automaticAttribution;
    Label listOfStudents;
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
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        previous = new MyButton("Previous");
        next = new MyButton("Next");
        automaticAttribution = new MyButton("Automatic Attribution");
        listOfStudents = new Label();

        setCenter(new HBox(automaticAttribution,listOfStudents));
        setRight(new VBox(previous,next));
    }
}
