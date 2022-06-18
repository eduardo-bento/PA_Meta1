package pt.isec.pa.apoio_poe.model.FX.Phase1.TeacherFx;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ReadFromFile;

public class TeacherFx extends BorderPane {
    TextField nameField;
    TextField emailField;
    ModelManager model;
    TeacherList list;
    EditTeacher edit;
    MyButton insert;

    MyButton previous;

    TextField idField;
    MyButton remove;
    ReadFromFile readFromFile;

    public TeacherFx(ModelManager model) {
        this.model = model;
        list = new TeacherList(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {this.setVisible(model != null && model.getState() == EState.TEACHER);}

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        previous.setOnAction(event -> {
            model.back();
        });

        remove.setOnAction(event -> {
            if (!model.remove(idField.getText())){
                idField.setStyle("-fx-background-color: #FF0000;");
            } else{
                idField.setStyle(null);
            }
        });

        insert.setOnAction(event -> {
            String email,name;
            name = nameField.getText();
            email = emailField.getText();

            if (emailField.getText().isBlank()){
                emailField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                emailField.setStyle(null);
            }
            if (nameField.getText().isBlank()){
                nameField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                nameField.setStyle(null);
            }

            if(!model.insert(new Teacher(email,name))){
                insert.setStyle("-fx-background-color: #FF0000;");
            } else{
                insert.setStyle("-fx-background-color: #D0C9C0;");
            }
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");
        VBox vBox = new VBox();
        insert = new MyButton("Insert");

        previous = new MyButton("Back");

        idField = new TextField();
        remove = new MyButton("Remove");

        readFromFile = new ReadFromFile(model);

        VBox removeBox = new VBox(remove,idField);
        //r.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        removeBox.setMaxWidth(200);
        removeBox.setMinWidth(200);
        removeBox.setMaxHeight(400);
        removeBox.setMinHeight(400);
        removeBox.setSpacing(30);
        removeBox.setAlignment(Pos.CENTER);

        emailField = new TextField();
        emailField.setPromptText("Enter your email.");
        nameField = new TextField();
        nameField.setPromptText("Enter your name.");


        vBox.getChildren().addAll(insert,emailField,nameField);
        vBox.setMaxWidth(200);
        vBox.setMinWidth(200);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setAlignment(Pos.CENTER);

        edit = new EditTeacher(model, list);
        HBox center = new HBox(vBox,readFromFile,edit);
        center.setSpacing(80);

        center.setAlignment(Pos.CENTER);

        setCenter(center);
        setLeft(list);
        setRight(previous);
    }
}