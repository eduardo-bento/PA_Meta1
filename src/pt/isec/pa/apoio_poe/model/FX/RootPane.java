package pt.isec.pa.apoio_poe.model.FX;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import pt.isec.pa.apoio_poe.model.Data.ConfigurationClosed;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.ProposalFx.ProposalFx;

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
                new ConfigurationFx(model),new ConfigurationClosed(model),
                new StudentFx(model),new ProposalFx(model));
        this.setCenter(stackPane);
        setTop(model.getTopMenu());
    }

    private void registerHandlers() { }

    private void update() { }
}
