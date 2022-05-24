package pt.isec.pa.apoio_poe.model.FX.ProposalFx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.ListPane;
import pt.isec.pa.apoio_poe.model.FX.StatusBar;
import pt.isec.pa.apoio_poe.model.Log;

public class ProposalFx extends BorderPane {
    ModelManager model;
    ChoiceBox<String> option;
    VBox project,interShip,selfProposal;
    StackPane stackPane;
    TextField idField;
    Button remove;
    ListPane list;

    public ProposalFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.PROPOSAL);
    }

    private void registerHandlers() {
        interShip.setVisible(false);
        selfProposal.setVisible(false);

        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        option.setOnAction(event ->  {
            if (option.getValue().equals("Project")){
                project.setVisible(true);
                interShip.setVisible(false);
                selfProposal.setVisible(false);
            } else if (option.getValue().equals("InterShip")){
                project.setVisible(false);
                interShip.setVisible(true);
                selfProposal.setVisible(false);
            } else if (option.getValue().equals("SelfProposal")){
                project.setVisible(false);
                interShip.setVisible(false);
                selfProposal.setVisible(true);
            }
        });

    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        list = new ListPane(model);
        project = new ProjectFx(model);
        interShip = new InterShipFx(model);
        selfProposal = new P(model);
        option = new ChoiceBox<>();
        option = new ChoiceBox(FXCollections.observableArrayList("Project","InterShip","SelfProposal"));
        option.setValue("Project");

        stackPane = new StackPane(project,interShip,selfProposal);
        this.setPadding(new Insets(50));
        stackPane.setAlignment(Pos.CENTER_LEFT);
        stackPane.setTranslateY(100);

        VBox type = new VBox(new Label("Type:"),option);
        option.setStyle("-fx-background-color: #D0C9C0;");
        type.setEffect(new DropShadow());
        type.setPadding(new Insets(4));
        type.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        type.setMaxWidth(150);
        type.setAlignment(Pos.CENTER);

        idField = new TextField();
        idField.setPromptText("Enter your id.");
        remove = new Button("Remove");
        remove.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");
        VBox qwe = new VBox(new Label("Remove"),idField,remove);
        qwe.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        qwe.setMaxHeight(50);
        qwe.setPadding(new Insets(4));

        HBox qweqwq = new HBox(new VBox(type,stackPane),qwe,list);
        qweqwq.setSpacing(20);
        qweqwq.setAlignment(Pos.CENTER);

        setCenter(qweqwq);
        setBottom(new StatusBar(model));
    }
}
