package pt.isec.pa.apoio_poe.model.FX.Phase5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class ExportToFile extends VBox {
    protected final ModelManager model;

    protected final TextField filePath = new TextField();
    protected final Button exportToFile = new Button("Export To File");

    public ExportToFile(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers(){
        exportToFile.setOnAction(event -> {
            String fileName;
            fileName = exportToFile.getText();
            if (fileName.isEmpty()){
                return;
            }
            model.exportFile(fileName);
        });
    }

    private void createViews() {
        filePath.setPromptText("Enter the file name.");

        this.getChildren().addAll(exportToFile,filePath);

        this.setLayoutX(10);
        this.setLayoutY(10);
        this.setMaxWidth(300);
        this.setPrefWidth(300);
        this.setPadding(new Insets(30));
        exportToFile.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        setSpacing(10);
        setAlignment(Pos.CENTER);
    }
}
