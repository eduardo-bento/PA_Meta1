package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class InsertProposal extends VBox {
    protected final ModelManager model;
    protected final TextField idField = new TextField();
    protected final TextField studentField = new TextField();
    protected final TextField titleField = new TextField();
    protected final Button insert = new Button("Insert");

    public InsertProposal(ModelManager model) {
        this.model = model;
        createViews();
    }

    private void createViews() {
        idField.setPromptText("Enter your id.");
        titleField.setPromptText("Enter your title.");
        studentField.setPromptText("Enter student id.");
        this.getChildren().addAll(insert,idField,titleField,studentField);

        this.setLayoutX(10);
        this.setLayoutY(10);
        this.setMaxWidth(300);
        this.setPrefWidth(300);
        //this.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        //this.setPadding(new Insets(30));
        //this.setEffect(new DropShadow());
        insert.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        setSpacing(30);
        maxWidth(300);
        this.setAlignment(Pos.CENTER);
    }
}
