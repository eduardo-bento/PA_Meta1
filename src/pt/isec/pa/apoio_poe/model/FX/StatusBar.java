package pt.isec.pa.apoio_poe.model.FX;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Log;

public class StatusBar extends HBox {
    ModelManager model;
    Label lbMsg;

    public StatusBar(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        Label lbMsgTitle = new Label("Message: ");
        lbMsgTitle.setPrefWidth(Integer.MAX_VALUE);
        lbMsgTitle.setAlignment(Pos.CENTER_RIGHT);
        lbMsg = new Label();
        lbMsg.setPrefWidth(Integer.MAX_VALUE);
        lbMsg.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(lbMsgTitle, lbMsg);
        setStyle("-fx-background-color: #D0C9C0;");
    }

    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
    }

    private void update() {
        lbMsg.setText(Log.getInstance().getMessages().toString());
        Log.getInstance().reset();
    }
}

