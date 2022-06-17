package pt.isec.pa.apoio_poe.model.FX;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Log;

import java.util.ArrayList;
import java.util.List;

public class StatusBar extends HBox {
    ModelManager model;
    Label lbMsg;
    List<String> message;

    public StatusBar(ModelManager model) {
        this.model = model;

        createViews();
        registerHandlers();
    }

    private void createViews() {
        message = new ArrayList<>();
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
        if (!Log.getInstance().getMessages().isEmpty()){
            message = List.copyOf(Log.getInstance().getMessages());
            lbMsg.setText(message.toString());
        }
        Log.getInstance().reset();
    }
}

