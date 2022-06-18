package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Phase2.FilterList.ProjectsList;
import pt.isec.pa.apoio_poe.model.FX.Phase2.FilterList.ProposalWithCandidacyList;
import pt.isec.pa.apoio_poe.model.FX.Phase2.FilterList.ProposalWithoutCandidacyList;
import pt.isec.pa.apoio_poe.model.FX.Phase2.FilterList.SelfProposalList;
import pt.isec.pa.apoio_poe.model.FX.Phase3.FilterList.AlreadyAttributedList;
import pt.isec.pa.apoio_poe.model.FX.Phase3.FilterList.AvailableProposalsList;

public class FilterProposal extends VBox {
    ModelManager model;
    ChoiceBox<String> candidacy;
    StackPane stackPane;
    AlreadyAttributedList alreadyAttributedList;
    AvailableProposalsList availableProposalsList;
    SelfProposalList selfProposalList;
    ProjectsList projectsList;
    public FilterProposal(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers() {
        candidacy.setOnAction(event -> {
            switch (candidacy.getValue()){
                case "Proposals already attributed" -> {
                    alreadyAttributedList.setVisible(true);
                    availableProposalsList.setVisible(false);
                    projectsList.setVisible(false);
                    selfProposalList.setVisible(false);
                }
                case "Available proposals" -> {
                    alreadyAttributedList.setVisible(false);
                    availableProposalsList.setVisible(true);
                    projectsList.setVisible(false);
                    selfProposalList.setVisible(false);
                }
                case "Teacher proposals" ->{
                    alreadyAttributedList.setVisible(false);
                    availableProposalsList.setVisible(false);
                    projectsList.setVisible(true);
                    selfProposalList.setVisible(false);
                }
                case "SelfProposals" ->{
                    alreadyAttributedList.setVisible(false);
                    availableProposalsList.setVisible(false);
                    projectsList.setVisible(false);
                    selfProposalList.setVisible(true);
                }
            }
        });
    }

    private void createViews() {
        candidacy = new ChoiceBox(FXCollections.observableArrayList(
                "SelfProposals","Teacher proposals",
                "Available proposals","Proposals already attributed")
        );
        candidacy.setValue("Available proposals");
        candidacy.setStyle("-fx-background-color: #D0C9C0;");

        alreadyAttributedList = new AlreadyAttributedList(model);
        availableProposalsList = new AvailableProposalsList(model);
        projectsList = new ProjectsList(model);
        selfProposalList = new SelfProposalList(model);

        candidacy.setMinWidth(250);
        stackPane = new StackPane(alreadyAttributedList,availableProposalsList,projectsList,selfProposalList);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(candidacy,stackPane);
    }
}
