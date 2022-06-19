package pt.isec.pa.apoio_poe.model.FX.Phase5;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Phase5.List.AvailableProposalsList;
import pt.isec.pa.apoio_poe.model.FX.Phase5.List.FinalProposals;
import pt.isec.pa.apoio_poe.model.FX.Phase5.List.StudentsWithAssignedProposal;
import pt.isec.pa.apoio_poe.model.FX.Phase5.List.StudentsWithoutFinalProposalAndWithCandidacy;

public class Data extends VBox {
    ModelManager model;
    StudentsWithAssignedProposal studentsWithAssignedProposal;
    StudentsWithoutFinalProposalAndWithCandidacy studentsWithoutFinalProposalAndWithCandidacy;
    FinalProposals finalProposals;
    AvailableProposalsList availableProposalsList;
    HBox teachersValues;
    Label average,highest,lowest;

    public Data(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        average.setText(String.valueOf(model.getTeachersAverage()));
        lowest.setText(String.valueOf(model.getTeachersLowest()));
        highest.setText(String.valueOf(model.getTeachersHighest()));
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void createViews() {
        studentsWithAssignedProposal = new StudentsWithAssignedProposal(model);
        studentsWithAssignedProposal.setMaxHeight(200);
        studentsWithoutFinalProposalAndWithCandidacy = new StudentsWithoutFinalProposalAndWithCandidacy(model);
        studentsWithoutFinalProposalAndWithCandidacy.setMaxHeight(200);
        finalProposals = new FinalProposals(model);
        finalProposals.setMaxHeight(200);
        availableProposalsList = new AvailableProposalsList(model);
        availableProposalsList.setMaxHeight(200);

        average = new Label(String.valueOf(model.getTeachersAverage()));
        lowest = new Label(String.valueOf(model.getTeachersLowest()));
        highest = new Label(String.valueOf(model.getTeachersHighest()));

        teachersValues = new HBox(new VBox(new Label("Average"),average),
                new VBox(new Label("Lowest"),lowest),
                new VBox(new Label("Highest"),highest));

        teachersValues.setSpacing(10);
        teachersValues.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(new HBox(new VBox(new Label("Students with assigned proposal"),studentsWithAssignedProposal),
                        new VBox(new Label("Students with final proposal and with candidacy"),studentsWithoutFinalProposalAndWithCandidacy)),
                        new HBox(new VBox(new Label("Final Proposals"),finalProposals),
                        new VBox(new Label("Available Proposals"),availableProposalsList)),teachersValues);

    }
}
