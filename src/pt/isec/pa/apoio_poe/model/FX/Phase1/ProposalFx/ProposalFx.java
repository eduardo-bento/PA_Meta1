package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ReadFromFile;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List.ProposalList;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List.StudentList;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List.TeacherList;

public class ProposalFx extends BorderPane {
    ModelManager model;
    ChoiceBox<String> option;
    VBox project,interShip,selfProposal;
    StackPane stackPane;
    StudentList students;
    TeacherList teachers;
    Edit edit;
    TextField idField;
    Button remove;
    ProposalList list;
    MyButton previous;

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
            switch (option.getValue()){
                case "Project" -> {
                    project.setVisible(true);
                    interShip.setVisible(false);
                    selfProposal.setVisible(false);
                }
                case "InterShip" -> {
                    project.setVisible(false);
                    interShip.setVisible(true);
                    selfProposal.setVisible(false);
                }
                case "SelfProposal" ->{
                    project.setVisible(false);
                    interShip.setVisible(false);
                    selfProposal.setVisible(true);
                }
            }
        });

        previous.setOnAction(event -> {
            model.back();
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");
        list = new ProposalList(model);

        project = new ProjectFx(model);
        edit = new Edit(model,list);

        interShip = new InterShipFx(model);
        selfProposal = new SelfProposalFx(model);

        teachers =  new TeacherList(model, (ProjectFx) project);
        previous = new MyButton("Previous");

        option = new ChoiceBox(FXCollections.observableArrayList("Project","InterShip","SelfProposal"));
        option.setValue("Project");

        students = new StudentList(model, (ProjectFx) project,(InterShipFx) interShip,(SelfProposalFx) selfProposal,option);
        stackPane = new StackPane(project,interShip,selfProposal);
        this.setPadding(new Insets(50));
        //stackPane.setAlignment(Pos.CENTER_LEFT);

        VBox type = new VBox(new Label("Type:"),option);
        option.setStyle("-fx-background-color: #D0C9C0;");
        //type.setEffect(new DropShadow());
        type.setPadding(new Insets(4));
        //type.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        //type.setPrefHeight(150);

        idField = new TextField();
        remove = new Button("Remove");
        remove.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");
        VBox removeBox = new VBox(remove,idField);
        //removeBox.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #EFEAD8;");
        removeBox.setMaxHeight(50);
        removeBox.setPadding(new Insets(12));
        removeBox.setAlignment(Pos.CENTER);
        removeBox.setSpacing(30);

        HBox blocks = new HBox(stackPane,edit);
        blocks.setSpacing(20);
        blocks.setPadding(new Insets(20));
        blocks.setAlignment(Pos.CENTER);

        VBox left = new VBox(type,previous,new VBox(new Label("Students"),students),new VBox(new Label("Teachers"),teachers));
        left.setSpacing(10);


        setLeft(left);
        setCenter(blocks);
        VBox right = new VBox(new ReadFromFile(model),list);
        right.setAlignment(Pos.CENTER);
        right.setSpacing(10);
        setRight(right);
    }
}
