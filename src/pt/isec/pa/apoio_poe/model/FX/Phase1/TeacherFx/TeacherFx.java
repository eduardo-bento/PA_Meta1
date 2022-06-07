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

        insert.setOnAction(insert -> {
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
                System.out.println("nao foi possivel adicioanr");
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

        VBox r = new VBox(idField,remove);
        //r.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        r.setMaxWidth(200);
        r.setMinWidth(200);
        r.setMaxHeight(400);
        r.setMinHeight(400);
        r.setSpacing(10);
        r.setAlignment(Pos.CENTER);

        emailField = new TextField();
        emailField.setPromptText("Enter your email.");
        nameField = new TextField();
        nameField.setPromptText("Enter your name.");


        vBox.getChildren().addAll(emailField,nameField,insert);
        vBox.setMaxWidth(200);
        vBox.setMinWidth(200);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER_LEFT);

        edit = new EditTeacher(model, list);
        HBox center = new HBox(vBox,r,readFromFile,edit);
        center.setSpacing(80);

        center.setAlignment(Pos.BASELINE_CENTER);

        setCenter(center);
        setLeft(list);
        setRight(previous);
    }
}