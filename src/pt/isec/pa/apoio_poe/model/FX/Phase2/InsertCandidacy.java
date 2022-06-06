package pt.isec.pa.apoio_poe.model.FX.Phase2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.Candidacy.Candidacy;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

import java.util.Arrays;
import java.util.HashSet;

import static pt.isec.pa.apoio_poe.utils.Utils.splitString;

public class InsertCandidacy extends VBox {
    protected final ModelManager model;

    protected final TextField studentField = new TextField();
    protected final TextField listProposals = new TextField();
    protected final Button insert = new Button("Insert");

    public InsertCandidacy(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers(){
        insert.setOnAction(event -> {
            Long id = null;
            try {
                id = Long.parseLong(studentField.getText());
                if (!model.remove(id)){
                    studentField.setStyle("-fx-background-color: #FF0000;");
                } else{
                    studentField.setStyle(null);
                }
            } catch (NumberFormatException e){
                studentField.setStyle("-fx-background-color: #FF0000;");
                return;
            }
            String proposals = listProposals.getText();
            String[] arr = proposals.split(",");

            if (arr.length == 0){
                listProposals.setStyle("-fx-background-color: #FF0000;");
            } else {
                listProposals.setStyle(null);
                model.insert(new Candidacy(id,new HashSet<>(Arrays.asList(arr))));
            }
        });
    }

    private void createViews() {
        listProposals.setPromptText("Enter the proposals.");
        studentField.setPromptText("Enter student id.");
        this.getChildren().addAll(insert,studentField,listProposals);

        this.setLayoutX(10);
        this.setLayoutY(10);
        this.setMaxWidth(300);
        this.setPrefWidth(300);
        this.setPadding(new Insets(30));
        insert.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        setSpacing(10);
        setAlignment(Pos.CENTER);
    }
}
