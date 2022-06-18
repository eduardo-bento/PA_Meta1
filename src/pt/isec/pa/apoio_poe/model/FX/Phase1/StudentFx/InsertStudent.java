package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

public class InsertStudent extends VBox {
    private ModelManager model;
    TextField idField;
    TextField nameField;
    TextField emailField;
    MyButton insert;
    Slider slider;
    ChoiceBox<Boolean> stageOption;
    ChoiceBox<String> curse;
    ChoiceBox<String> branch;


    public InsertStudent(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        insert.setOnAction(event -> {
            Long id = null;
            String email,name;
            name = nameField.getText();
            email = emailField.getText();

            try {
                id = Long.valueOf(idField.getText());
                idField.setStyle(null);
            } catch (NumberFormatException e){
                idField.setStyle("-fx-background-color: #FF0000;");
                return;
            }
            if (nameField.getText().isBlank()){
                nameField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                nameField.setStyle(null);
            }

            if (emailField.getText().isBlank()){
                emailField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                emailField.setStyle(null);
            }

            if(!model.insert(new Student(id,name,email,curse.getValue(),branch.getValue(),slider.getValue(),stageOption.getValue()))){
                insert.setStyle("-fx-background-color: #FF0000;");
            } else {
                insert.setStyle("-fx-background-color: #D0C9C0;");
            }

        });
    }

    private void createViews() {
        //this.setPadding(new Insets(10));
        //this.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");

        stageOption = new ChoiceBox<>();
        Label classificationTitle = new Label("Classification");
        Label  value  = new Label();
        slider = new Slider();

        slider.setMin(0);
        slider.setMax(1);
        slider.setValue(0.5);

        value.textProperty().bind(
                Bindings.format(
                        "%.2f",
                        slider.valueProperty()
                )
        );
        HBox v = new HBox(slider,value);
        v.setAlignment(Pos.CENTER);
        VBox classification = new VBox(classificationTitle,v);
        classification.maxWidth(200);
        insert = new MyButton("Insert");


        stageOption = new ChoiceBox(FXCollections.observableArrayList(
                true,false)
        );
        stageOption.setValue(true);

        Label stage = new Label("Has stage?");
        stageOption.setStyle("-fx-background-color: #D0C9C0;");
        VBox typesBox = new VBox(stage, stageOption);

        curse = new ChoiceBox(FXCollections.observableArrayList(
                "LEI","LEI-PL")
        );
        curse.setValue("LEI");
        curse.setStyle("-fx-background-color: #D0C9C0;");
        VBox curseBox = new VBox(new Label("Curse"), curse);


        branch = new ChoiceBox(FXCollections.observableArrayList(
                "DA","RAS","SI")
        );
        branch.setValue("DA");
        branch.setStyle("-fx-background-color: #D0C9C0;");
        VBox branchBox = new VBox(new Label("Branch"), branch);

        idField = new TextField();
        idField.setPromptText("Enter your id.");
        nameField = new TextField();
        nameField.setPromptText("Enter your name.");
        emailField = new TextField();
        emailField.setPromptText("Enter your email.");

        HBox chooseBoxes = new HBox(typesBox,curseBox,branchBox);
        chooseBoxes.setSpacing(10);
        chooseBoxes.setAlignment(Pos.CENTER);
        classification.setAlignment(Pos.CENTER);
        this.getChildren().addAll(insert,idField,nameField,emailField,chooseBoxes,classification);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(250);
        this.setMinWidth(250);
        this.setMaxHeight(250);
        this.setPadding(new Insets(10));
        this.setMinHeight(250);
        //this.setStyle("-fx-background-radius: 6; -fx-background-color: #5A6E5D;");
        this.setSpacing(10);
    }


}
