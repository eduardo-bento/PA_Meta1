package pt.isec.pa.apoio_poe.model.FX.Phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

import java.util.List;

public class CandidacyClosedFx extends BorderPane {
    ModelManager model;
    ListPane listPane;
    Label listOfStudents;
    MyButton next,previous;

    public CandidacyClosedFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CANDIDACY_PHASE_LOCK);
        listOfStudents.setText(model.getListOfStudents());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());

        next.setOnAction(event -> {
            model.forward();
        });

        previous.setOnAction(event -> {
            model.back();
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        listOfStudents = new Label();
        listOfStudents.setAlignment(Pos.CENTER);
        listOfStudents.setMinWidth(200);
        listOfStudents.setText(model.getListOfStudents());
        listOfStudents.setPadding(new Insets(10));
        listOfStudents.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");


        listPane = new ListPane(model);

        next = new MyButton("Next");
        previous = new MyButton("Back");

        setLeft(listPane);
        VBox right = new VBox(previous,next);
        right.setSpacing(10);

        setRight(right);
        setCenter(listOfStudents);
    }
}
