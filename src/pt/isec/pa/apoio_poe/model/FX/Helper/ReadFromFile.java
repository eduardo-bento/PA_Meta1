package pt.isec.pa.apoio_poe.model.FX.Helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ReadFromFile extends VBox {
    ModelManager model;
    TextField idField;
    MyButton readFromFile;

    public ReadFromFile(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void createViews() {
        idField = new TextField();
        readFromFile = new MyButton("Read From File");
        this.getChildren().addAll(readFromFile,idField);
        this.setPadding(new Insets(10));
        //this.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        readFromFile.setMinWidth(200);
        idField.setMinWidth(200);
        this.setMaxWidth(200);
        this.setMinWidth(200);
        //this.setMaxHeight(400);
        //this.setMinHeight(400);

        this.setSpacing(10);

        this.setAlignment(Pos.CENTER);
    }

    private void registerHandlers(){
        readFromFile.setOnAction(event -> {
            if(!model.readFromFile(idField.getText())){
                readFromFile.setStyle("-fx-background-color: #FF0000;");
            } else {
                readFromFile.setStyle("-fx-background-color: #D0C9C0;");
            }
        });
    }
}
