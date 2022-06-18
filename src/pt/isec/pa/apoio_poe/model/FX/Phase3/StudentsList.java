package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Phase3.List.StudentsSelfProposal;
import pt.isec.pa.apoio_poe.model.FX.Phase3.List.StudentsWithCandidacy;
import pt.isec.pa.apoio_poe.model.FX.Phase3.List.StudentsWithNoProposal;

public class StudentsList extends VBox {
    private ModelManager model;
    private StudentsWithNoProposal noProposal;
    private StudentsSelfProposal selfProposal;
    private StudentsWithCandidacy withCandidacy;

    public StudentsList(ModelManager model) {
        this.model = model;
        createViews();
    }

    private void createViews() {
        noProposal = new StudentsWithNoProposal(model);
        selfProposal = new StudentsSelfProposal(model);
        withCandidacy = new StudentsWithCandidacy(model);

        this.getChildren().addAll(new VBox(new Label("Students with Candidacy"),withCandidacy)
                ,new VBox(new Label("Students with SelfProposal"),noProposal),
                new VBox(new Label("Students with no Proposal"),selfProposal));

        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }

}
