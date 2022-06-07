package pt.isec.pa.apoio_poe.model.FX.Phase3;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.FX.Helper.MyButton;


public class TieBreakerFx extends BorderPane {
    ModelManager model;
    Label data;
    TextField proposalId,studentId;
    MyButton register;

    public TieBreakerFx(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
        this.setVisible(model != null && model.getState() == EState.TIEBREAKER);
    }

    private void createViews() {
        data = new Label();
        proposalId = new TextField();
        studentId = new TextField();
        register = new MyButton("Register");

        setCenter(new VBox(proposalId,studentId,register));
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());

        register.setOnAction(event -> {
            Long id = null;
            if (proposalId.getText().isEmpty()){
                proposalId.setStyle("-fx-background-color: #FF0000;");
                return;
            } else {
                proposalId.setStyle(null);
            }

            try {
                id = Long.parseLong(studentId.getText());
                studentId.setStyle(null);
                model.handleConflict(id,proposalId.getText());
            } catch (NumberFormatException e){
                studentId.setStyle("-fx-background-color: #FF0000;");
            }
        });
    }
}
