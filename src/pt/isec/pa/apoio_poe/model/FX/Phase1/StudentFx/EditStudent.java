package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

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
            if(list.getSelected() == null){
                button.setStyle("-fx-background-color: #FF0000;");
                return;
            } else{
                button.setStyle("-fx-background-color: #D0C9C0;");
                field.setStyle(null);
            }

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
                    field.setStyle(null);
                    model.edit(list.getSelected().getId(),value,"hasStage", Student.class);
                } catch (NumberFormatException e){
                    field.setStyle("-fx-background-color: #FF0000;");
                }
            } else if (choiceBox.getValue().equals("curse")){
                model.edit(list.getSelected().getId(),field.getText(),choiceBox.getValue(), Student.class);
            } else if(choiceBox.getValue().equals("branch")){
                model.edit(list.getSelected().getId(),field.getText(),choiceBox.getValue(), Student.class);
            }
        });

        choiceBox.setOnAction(event -> {
            field.setPromptText("Change " + choiceBox.getValue());
        });
    }

    private void createViews() {
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "email","classification","stage","curse","branch")
        );

        choiceBox.setValue("email");
        field = new TextField();
        button = new MyButton("Change");
        choiceBox.setStyle("-fx-background-color: #D0C9C0;");

        VBox choose = new VBox(button,choiceBox,field);
        choose.setSpacing(10);
        choose.setAlignment(Pos.CENTER);

        this.setMaxWidth(250);
        this.setMinWidth(250);
        this.setMaxHeight(150);
        this.setPadding(new Insets(10));
        this.setMinHeight(150);
        //this.setStyle("-fx-background-radius: 6; -fx-background-color: #5A6E5D;");

        field.setPromptText("Change " + choiceBox.getValue());
        this.getChildren().addAll(choose);
        this.setAlignment(Pos.CENTER);
    }

}
