package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.InterShip;

public class InterShipFx extends InsertProposal {
    private final TextField entityField = new TextField();
    private ChoiceBox<String> destiny;

    public InterShipFx(ModelManager model) {
        super(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        insert.setOnAction(insert -> {
            Long id;
            if (idField.getText().isBlank()){
                idField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                idField.setStyle(null);
            }
            if (titleField.getText().isBlank()){
                titleField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                titleField.setStyle(null);
            }
            if (entityField.getText().isBlank()){
                entityField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                entityField.setStyle(null);
            }
            try {
                id = Long.valueOf(studentField.getText());
                studentField.setStyle(null);
                model.insert(new InterShip(idField.getText(),titleField.getText(),id,destiny.getValue(),entityField.getText()));
            } catch (NumberFormatException e){
                studentField.setStyle("-fx-background-color: #FF0000;");
            }
        });
    }

    private void createViews() {
        entityField.setPromptText("Enter your entity.");
        destiny = new ChoiceBox(FXCollections.observableArrayList("DA","SI","RAS"));
        destiny.setValue("DA");
        destiny.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        this.getChildren().addAll(entityField,destiny);
    }
}