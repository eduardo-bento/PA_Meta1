package pt.isec.pa.apoio_poe.model.FX.Phase4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ManualRemove extends VBox {
    protected final ModelManager model;

    protected final TextField proposalField = new TextField();
    protected final Button remove = new Button("Manual Remove");

    public ManualRemove(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers(){
        remove.setOnAction(event -> {
            String prosposalId;
            prosposalId = proposalField.getText();


            if (proposalField.getText().isBlank()){
                proposalField.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                proposalField.setStyle(null);
            }

            model.manualTeacherRemove(prosposalId);
        });
    }

    private void createViews() {
        proposalField.setPromptText("Enter the proposals id.");

        this.getChildren().addAll(remove,proposalField);

        this.setLayoutX(10);
        this.setLayoutY(10);
        this.setMaxWidth(300);
        this.setPrefWidth(300);
        this.setPadding(new Insets(30));
        remove.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        setSpacing(10);
        setAlignment(Pos.CENTER);
    }
}
