package pt.isec.pa.apoio_poe.model.FX.Phase1.TeacherFx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx.StudentList;
import pt.isec.pa.apoio_poe.model.Log;

public class EditTeacher extends VBox {
    ModelManager model;
    TextField field;
    MyButton button;
    TeacherList list;

    public EditTeacher(ModelManager model, TeacherList list){
        this.model = model;
        this.list = list;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        button.setOnAction(event -> {
            if (list.getSelected() == null) {
                button.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                button.setStyle("-fx-background-color: #D0C9C0;");
            }
            if(!model.edit(list.getSelected().getEmail(),field.getText(),"name",Teacher.class)){
                button.setStyle("-fx-background-color: #FF0000;");
            } else{
                button.setStyle("-fx-background-color: #D0C9C0;");
            }
        });
    }

    private void createViews() {

        field = new TextField();
        field.setPromptText("change name");
        button = new MyButton("Change");

        this.getChildren().addAll(button,field);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

}
