package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import com.sun.javafx.scene.control.FakeFocusTextField;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

public class EditStudent extends VBox {
    ModelManager model;
    ChoiceBox<String> choiceBox;
    TextField field;
    MyButton button;
    StudentList list;

    public EditStudent(ModelManager model,StudentList list){
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
            if (choiceBox.getValue().equals("email")) {
                model.edit(list.getSelected().getId(),field.getText(),choiceBox.getValue(), Student.class);
            } else if (choiceBox.getValue().equals("classification")){
                double id;
                try {
                    id = Double.parseDouble(field.getText());
                    field.setStyle(null);
                    model.edit(list.getSelected().getId(),id,choiceBox.getValue(), Student.class);
                } catch (NumberFormatException e){
                    field.setStyle("-fx-background-color: #FF0000;");
                }
            } else if (choiceBox.getValue().equals("hasStage")){
                boolean value;
                try {
                    value = Boolean.parseBoolean(field.getText());
                    System.out.println(value);
                    field.setStyle(null);
                    model.edit(list.getSelected().getId(),value,choiceBox.getValue(), Student.class);
                } catch (NumberFormatException e){
                    field.setStyle("-fx-background-color: #FF0000;");
                }
            }
        });
    }

    private void createViews() {
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "email","classification","hasStage")
        );

        choiceBox.setValue("email");
        field = new TextField();
        button = new MyButton("Change");

        this.getChildren().addAll(button,choiceBox,field);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

}
