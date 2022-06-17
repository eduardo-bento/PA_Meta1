package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.InterShip;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;

public class InterShipFx extends InsertProposal {
    private final TextField entityField = new TextField();
    private ChoiceBox<String> destiny;
    private MyButton ras,si,da;

    public InterShipFx(ModelManager model) {
        super(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        ras.setOnAction(event -> {
            if (ras.getStyle().contains("green")){
                ras.setStyle("-fx-background-color: #D0C9C0;");
            } else{
                ras.setStyle("-fx-background-color: green;");
            }
        });

        da.setOnAction(event -> {
            if (da.getStyle().contains("green")){
                da.setStyle("-fx-background-color: #D0C9C0;");
            } else{
                da.setStyle("-fx-background-color: green;");
            }
        });

        si.setOnAction(event -> {
            if (si.getStyle().contains("green")){
                si.setStyle("-fx-background-color: #D0C9C0;");
            } else{
                si.setStyle("-fx-background-color: green;");
            }
        });

        insert.setOnAction(event -> {
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

            String branches = "";
            if (ras.getStyle().contains("green")){
                branches += "RAS";
            }
            if (da.getStyle().contains("green")){
                if (!branches.isEmpty()){
                    branches += "|";
                }
                branches += "DA";
            }
            if (si.getStyle().contains("green")){
                if (!branches.isEmpty()){
                    branches += "|";
                }
                branches += "SI";
            }
            System.out.println(branches);
            try {
                id = Long.valueOf(studentField.getText());
                studentField.setStyle(null);
                if(!model.insert(new InterShip(idField.getText(),titleField.getText(),id,branches,entityField.getText()))){
                    insert.setStyle("-fx-background-color: #FF0000;");
                } else {
                    insert.setStyle("-fx-background-color: #D0C9C0;");
                }
            } catch (NumberFormatException e){
                studentField.setStyle("-fx-background-color: #FF0000;");
            }
        });
    }

    private void createViews() {
        entityField.setPromptText("Enter your entity.");

        ras = new MyButton("RAS");
        si = new MyButton("SI");
        da = new MyButton("DA");
        HBox branchs = new HBox(ras,si,da);
        branchs.setSpacing(10);
        branchs.setAlignment(Pos.CENTER);

        destiny = new ChoiceBox(FXCollections.observableArrayList("DA","SI","RAS"));
        destiny.setValue("DA");
        destiny.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");

        this.getChildren().addAll(entityField,branchs);
    }
}