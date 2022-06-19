package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ReadFromFile;

public class StudentFx extends BorderPane {
    ModelManager model;
    InsertStudent insertStudent;
    StudentList listPane;
    ReadFromFile readFromFile;
    MyButton previous,remove;
    EditStudent edit;

    TextField idField;

    public StudentFx(ModelManager model) {
        this.model = model;
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

        listPane = new StudentList(model);
        previous = new MyButton("Back");
        insertStudent = new InsertStudent(model);
        readFromFile = new ReadFromFile(model);
        idField = new TextField();
        remove = new MyButton("Remove");

        edit = new EditStudent(model,listPane);

        VBox removeBox = new VBox(remove,idField);
        removeBox.setMaxWidth(200);
        removeBox.setMinWidth(200);
        removeBox.setMaxHeight(400);
        removeBox.setMinHeight(400);
        removeBox.setSpacing(30);
        removeBox.setAlignment(Pos.CENTER);

        HBox center = new HBox(insertStudent,edit);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(200);

        listPane.setMinHeight(500);
        VBox left = new VBox(readFromFile,listPane);
        left.setAlignment(Pos.CENTER);

        left.setSpacing(10);
        left.setPadding(new Insets(30));

        setCenter(center);
        setLeft(left);
        setRight(previous);
    }
}
