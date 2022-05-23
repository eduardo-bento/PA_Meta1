package pt.isec.pa.apoio_poe.model;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Student.Student;

public class StudentFx extends BorderPane {
    TextField idField;
    TextField nameField;
    TextField emailField;
    ModelManager model;
    Button insert;
    Slider slider;
    ChoiceBox<Boolean> stageOption;
    ChoiceBox<String> curse;
    ChoiceBox<String> branch;
    ListPane listPane;

    public StudentFx(ModelManager model) {
        this.model = model;
        listPane = new ListPane(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.STUDENT);
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        insert.setOnAction(insert -> {
            Long id;
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

            try {
                id = Long.valueOf(idField.getText());
                idField.setStyle(null);
                if(!model.insert(new Student(id,name,email,curse.getValue(),branch.getValue(),slider.getValue(),stageOption.getValue()))){
                    System.out.println("nao foi possivel adicioanr");
                }
            } catch (NumberFormatException e){
                idField.setStyle("-fx-background-color: #FF0000;");
            }
        });
    }

    private void createViews() {
        VBox vBox = new VBox();
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

        VBox classification = new VBox(classificationTitle,v);
        classification.maxWidth(200);
        insert = new Button("Insert");


        stageOption = new ChoiceBox(FXCollections.observableArrayList(
                true,false)
        );
        stageOption.setValue(true);
        VBox typesBox = new VBox(new Label("Has stage?"), stageOption);

        curse = new ChoiceBox(FXCollections.observableArrayList(
                "LEI","LEI-PL")
        );
        curse.setValue("LEI");
        VBox curseBox = new VBox(new Label("Curse"), curse);


        branch = new ChoiceBox(FXCollections.observableArrayList(
                "DA","RAS","SI")
        );
        branch.setValue("DA");
        VBox branchBox = new VBox(new Label("Branch"), branch);

        idField = new TextField();
        idField.setPromptText("Enter your id.");
        nameField = new TextField();
        nameField.setPromptText("Enter your name.");
        emailField = new TextField();
        emailField.setPromptText("Enter your email.");

        vBox.getChildren().addAll(nameField, idField, emailField,new HBox(typesBox,curseBox,branchBox),classification,insert);

        vBox.setMaxWidth(200);
        vBox.setMinWidth(200);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER_LEFT);


        setCenter(new HBox(vBox,listPane));
    }
}
