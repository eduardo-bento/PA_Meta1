package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Project;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List.ProposalList;

public class Edit extends VBox {
    ModelManager model;
    ChoiceBox<String> choiceBox;
    TextField field;
    MyButton button;
    ProposalList list;

    public Edit(ModelManager model, ProposalList list){
        this.model = model;
        this.list = list;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        button.setOnAction(event -> {
            if(list.getSelected() == null){
                button.setStyle("-fx-background-color: #FF0000;");
                return;
            } else{
                button.setStyle("-fx-background-color: #D0C9C0;");
                field.setStyle(null);
            }

            if (!field.getText().isEmpty()){
                field.setStyle("-fx-background-color: #FF0000;");
                return;
            } else{
                field.setStyle(null);
            }

            if (!(list.getSelected() instanceof Project)){
                return;
            }

            if (choiceBox.getValue().equals("title")) {
                model.edit(list.getSelected().getId(),field.getText(),choiceBox.getValue(), Proposal.class);
            } else if (choiceBox.getValue().equals("student")){
                Long id;
                try {
                    id = Long.parseLong(field.getText());
                    field.setStyle(null);
                    model.edit(list.getSelected().getId(),id,choiceBox.getValue(), Proposal.class);
                } catch (NumberFormatException e){
                    field.setStyle("-fx-background-color: #FF0000;");
                }
            }
        });

        choiceBox.setOnAction(event -> {
            field.setPromptText("Change " + choiceBox.getValue());
        });
    }

    private void createViews() {
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "title","student")
        );

        choiceBox.setValue("title");
        field = new TextField();
        button = new MyButton("Change");
        choiceBox.setStyle("-fx-background-color: #D0C9C0;");

        VBox choose = new VBox(choiceBox,button);
        choose.setSpacing(2);
        choose.setAlignment(Pos.CENTER);
        field.setPromptText("Change " + choiceBox.getValue());
        this.getChildren().addAll(choose,field);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(30);
    }

}
