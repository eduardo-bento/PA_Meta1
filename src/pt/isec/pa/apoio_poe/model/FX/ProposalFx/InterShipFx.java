package pt.isec.pa.apoio_poe.model.FX.ProposalFx;

import javafx.scene.control.TextField;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.InterShip;

public class InterShipFx extends P{
    private final TextField entityField = new TextField();

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
                if(!model.insert(new InterShip(idField.getText(),titleField.getText(),id,null,null))){
                    System.out.println("nao foi possivel adicioanr");
                }
            } catch (NumberFormatException e){
                studentField.setStyle("-fx-background-color: #FF0000;");
            }

        });
    }

    private void createViews() {
        entityField.setPromptText("Enter your entity.");
        this.getChildren().add(entityField);
    }
}
