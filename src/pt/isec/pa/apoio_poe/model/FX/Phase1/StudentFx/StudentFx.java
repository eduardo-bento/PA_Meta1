package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ReadFromFile;
import pt.isec.pa.apoio_poe.model.FX.Helper.Remove;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

public class StudentFx extends BorderPane {
    ModelManager model;
    InsertStudent insertStudent;
    StudentList listPane;
    ReadFromFile readFromFile;
    MyButton previous;

    TextField idField;
    MyButton remove;

    public StudentFx(ModelManager model) {
        this.model = model;
        listPane = new StudentList(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.STUDENT);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

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
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        previous = new MyButton("Back");
        insertStudent = new InsertStudent(model);
        readFromFile = new ReadFromFile(model);
        idField = new TextField();
        remove = new MyButton("Remove");

        VBox r = new VBox(idField,remove);
        r.setMaxWidth(200);
        r.setMinWidth(200);
        r.setMaxHeight(400);
        r.setMinHeight(400);
        r.setSpacing(10);
        r.setAlignment(Pos.CENTER);

        HBox center = new HBox(insertStudent,r,readFromFile);
        center.setAlignment(Pos.BASELINE_CENTER);
        center.setSpacing(40);

        setCenter(center);
        setLeft(listPane);
        setRight(previous);
    }
}
