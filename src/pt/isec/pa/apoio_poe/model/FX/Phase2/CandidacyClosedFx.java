package pt.isec.pa.apoio_poe.model.FX.Phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ListPane;
import pt.isec.pa.apoio_poe.model.FX.Phase2.List.StudentsWithCandidacy;
import pt.isec.pa.apoio_poe.model.FX.Phase2.List.StudentsWithNoProposal;
import pt.isec.pa.apoio_poe.model.FX.Phase2.List.StudentsWithoutCandidacy;

public class CandidacyClosedFx extends BorderPane {
    ModelManager model;
    ListPane listPane;
    Label listOfStudents;
    MyButton next,previous;
    FilterCandidacy filterCandidacy;
    StudentsWithoutCandidacy studentsWithoutCandidacy;
    StudentsWithCandidacy studentsWithCandidacy;
    StudentsWithNoProposal studentsWithNoProposal;

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

        studentsWithCandidacy = new StudentsWithCandidacy(model);
        studentsWithoutCandidacy = new StudentsWithoutCandidacy(model);
        studentsWithNoProposal = new StudentsWithNoProposal(model);

        filterCandidacy = new FilterCandidacy(model);

        listPane = new ListPane(model);

        VBox studentsList = new VBox(new VBox(new Label("Students without candidacy"),studentsWithoutCandidacy)
                ,new VBox(new Label("Students with candidacy"),studentsWithCandidacy),
                new VBox(new Label("Students with no proposal"),studentsWithNoProposal));
        studentsList.setAlignment(Pos.CENTER);
        studentsList.setSpacing(10);

        next = new MyButton("Next");
        previous = new MyButton("Back");

        HBox center = new HBox(studentsList,filterCandidacy);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(20);

        setLeft(listPane);
        VBox right = new VBox(previous,next);
        right.setSpacing(10);

        setRight(right);
        setCenter(center);
    }
}
