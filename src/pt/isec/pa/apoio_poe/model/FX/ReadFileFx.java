package pt.isec.pa.apoio_poe.model.FX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ReadFileFx extends VBox {
    ModelManager model;
    Button button;
    TextField textField;

    public ReadFileFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        button.setOnAction(event -> model.readFromFile(textField.getText()));
    }

    private void createViews() {
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        this.setStyle("-fx-background-radius: 6;" +  "-fx-background-color: #EFEAD8;");

        button = new Button("Read");
        textField = new TextField();
        textField.setPromptText("Enter the file path");

        getChildren().addAll(new Label("Read from file"),textField,button);
    }
}
