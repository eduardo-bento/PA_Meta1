package pt.isec.pa.apoio_poe.model.FX.Phase2;

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

public class FilterCandidacy extends VBox {
    ModelManager model;
    ChoiceBox<String> candidacy;
    StackPane stackPane;
    ProposalWithCandidacyList proposalWithCandidacyList;
    ProposalWithoutCandidacyList proposalWithoutCandidacyList;
    SelfProposalList selfProposalList;
    ProjectsList projectsList;
    public FilterCandidacy(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
    }

    private void registerHandlers() {
        candidacy.setOnAction(event -> {
            switch (candidacy.getValue()){
                case "Proposals with candidacy" -> {
                    proposalWithCandidacyList.setVisible(true);
                    proposalWithoutCandidacyList.setVisible(false);
                    projectsList.setVisible(false);
                    selfProposalList.setVisible(false);
                }
                case "Proposals without candidacy" -> {
                    proposalWithCandidacyList.setVisible(false);
                    proposalWithoutCandidacyList.setVisible(true);
                    projectsList.setVisible(false);
                    selfProposalList.setVisible(false);
                }
                case "Teacher proposals" ->{
                    proposalWithCandidacyList.setVisible(false);
                    proposalWithoutCandidacyList.setVisible(false);
                    projectsList.setVisible(true);
                    selfProposalList.setVisible(false);
                }
                case "SelfProposals" ->{
                    proposalWithCandidacyList.setVisible(false);
                    proposalWithoutCandidacyList.setVisible(false);
                    projectsList.setVisible(false);
                    selfProposalList.setVisible(true);
                }
            }
        });
    }

    private void createViews() {
        candidacy = new ChoiceBox(FXCollections.observableArrayList(
                "SelfProposals","Teacher proposals",
                "Proposals with candidacy","Proposals without candidacy")
        );
        candidacy.setValue("SelfProposals");
        candidacy.setStyle("-fx-background-color: #D0C9C0;");

        proposalWithCandidacyList = new ProposalWithCandidacyList(model);
        proposalWithoutCandidacyList = new ProposalWithoutCandidacyList(model);
        projectsList = new ProjectsList(model);
        selfProposalList = new SelfProposalList(model);

        candidacy.setMinWidth(250);
        stackPane = new StackPane(proposalWithCandidacyList,proposalWithoutCandidacyList,projectsList,selfProposalList);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(candidacy,stackPane);
    }
}
