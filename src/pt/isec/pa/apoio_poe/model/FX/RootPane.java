package pt.isec.pa.apoio_poe.model.FX;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Phase1.*;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.ProposalFx;
import pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx.StudentFx;
import pt.isec.pa.apoio_poe.model.FX.Phase1.TeacherFx.TeacherFx;
import pt.isec.pa.apoio_poe.model.FX.Phase2.CandidacyClosedFx;
import pt.isec.pa.apoio_poe.model.FX.Phase2.CandidacyFx;
import pt.isec.pa.apoio_poe.model.FX.Phase3.ProposalPhaseClosedFx;
import pt.isec.pa.apoio_poe.model.FX.Phase3.ProposalPhaseFx;
import pt.isec.pa.apoio_poe.model.FX.Phase3.ProposalSingleFx;
import pt.isec.pa.apoio_poe.model.FX.Phase3.TieBreakerFx;
import pt.isec.pa.apoio_poe.model.FX.Phase4.TeachersFx;
import pt.isec.pa.apoio_poe.model.FX.Phase5.QueryingFx;

public class RootPane extends BorderPane {
    ModelManager model;

    public RootPane(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        StackPane stackPane = new StackPane(
                new ConfigurationFx(model),new ConfigurationClosedFx(model),
                new StudentFx(model),new ProposalFx(model),
                new TeacherFx(model),new Template(model),
                new CandidacyFx(model),new CandidacyClosedFx(model), new ProposalSingleFx(model),
                new TieBreakerFx(model),new ProposalPhaseFx(model), new TeachersFx(model), new QueryingFx(model),
                new ProposalPhaseClosedFx(model));
        this.setCenter(stackPane);

        setTop(model.getTopMenu());
        setBottom(new StatusBar(model));
    }

    private void registerHandlers() { }

    private void update() { }
}
