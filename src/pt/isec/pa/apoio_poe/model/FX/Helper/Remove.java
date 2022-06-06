package pt.isec.pa.apoio_poe.model.FX.Helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;


public class Remove extends VBox {
    ModelManager model;
    TextField idField;
    MyButton remove;
    Label title;

    public Remove(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        idField = new TextField();
        remove = new MyButton("Remove");
        title = new Label("Remove");
        this.getChildren().addAll(title,idField,remove);
        this.setPadding(new Insets(10));



        this.setMaxWidth(200);
        this.setMinWidth(200);
        this.setMaxHeight(400);
        this.setMinHeight(400);

        this.setSpacing(10);

        this.setAlignment(Pos.CENTER);
    }

    private void registerHandlers() {
        remove.setOnAction(event -> {
            System.out.println(idField.getText());
            if (!model.remove(idField.getText())){
                idField.setStyle("-fx-background-color: ##FF0000;");
            } else{
                idField.setBackground(null);
            }
        });
    }

    private void update() {

    }
    public TextField getIdField() {
        return idField;
    }

    public MyButton getRemove() {
        return remove;
    }
}
