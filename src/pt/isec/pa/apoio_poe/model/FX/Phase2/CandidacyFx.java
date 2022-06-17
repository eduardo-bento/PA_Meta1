package pt.isec.pa.apoio_poe.model.FX.Phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ReadFromFile;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

public class CandidacyFx extends BorderPane {
    ModelManager model;
    CandidacyList list;
    ReadFromFile readFromFile;
    InsertCandidacy insertCandidacy;
    ListFilterProposal listFilterProposal;
    MyButton previous,closePhase,next;
    Label listOfStudents;

    TextField idField;
    MyButton remove;

    public CandidacyFx(ModelManager model) {
        this.model = model;
        list = new CandidacyList(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.CANDIDACY);
        listOfStudents.setText(model.getListOfStudents());
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());

        previous.setOnAction(event -> {
            model.back();
        });

        remove.setOnAction(event -> {
            long id;
            try {
                id = Long.parseLong(idField.getText());
                if (!model.remove(id)){
                    idField.setStyle("-fx-background-color: #FF0000;");
                } else{
                    idField.setStyle(null);
                }
            } catch (NumberFormatException e){
                idField.setStyle("-fx-background-color: #FF0000;");
            }
        });

        closePhase.setOnAction(event -> {
            model.closePhase();
        });

        next.setOnAction(event -> {
            model.forward();
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        idField = new TextField();
        remove = new MyButton("Remove");

        VBox removeBox = new VBox(remove,idField);
        //r.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        removeBox.setMaxWidth(200);
        removeBox.setMinWidth(200);
        removeBox.setMaxHeight(400);
        removeBox.setMinHeight(400);
        removeBox.setSpacing(30);
        removeBox.setAlignment(Pos.CENTER);

        previous = new MyButton("Back");
        closePhase = new MyButton("Close Phase");
        next = new MyButton("Next");
        readFromFile = new ReadFromFile(model);
        insertCandidacy = new InsertCandidacy(model);
        listFilterProposal = new ListFilterProposal(model);

        listOfStudents = new Label();
        listOfStudents.setAlignment(Pos.CENTER);
        listOfStudents.setMinWidth(200);
        listOfStudents.setText(model.getListOfStudents());
        listOfStudents.setPadding(new Insets(10));
        listOfStudents.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        HBox center = new HBox(readFromFile,insertCandidacy,listFilterProposal,new VBox(new Label("Students"),listOfStudents));
        center.setAlignment(Pos.BASELINE_CENTER);
        center.setSpacing(10);

        setCenter(center);
        setLeft(list);

        VBox right = new VBox(previous,next,closePhase);
        right.setSpacing(10);

        setRight(right);
    }
}
