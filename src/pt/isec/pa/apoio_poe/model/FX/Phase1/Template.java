package pt.isec.pa.apoio_poe.model.FX.Phase1;

import javafx.scene.layout.BorderPane;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;
import pt.isec.pa.apoio_poe.model.FX.Helper.ListPane;

public class Template extends BorderPane {
    private ModelManager model;
    private ListPane list;
    private MyButton previous;

    public Template(ModelManager model){
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {this.setVisible(model != null && (model.getState() == EState.TEACHER_LOCK ||
            model.getState() == EState.STUDENT_LOCK || model.getState() == EState.PROPOSAL_LOCK));}

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        previous.setOnAction(event -> {
            model.back();
        });
    }

    private void createViews() {
        this.setStyle("-fx-background-color: #5F7161;");

        list = new ListPane(model);
        previous = new MyButton("Previous");
        setLeft(list);
        setCenter(previous);
    }
}
