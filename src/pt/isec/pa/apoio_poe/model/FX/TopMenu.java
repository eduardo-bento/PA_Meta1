package pt.isec.pa.apoio_poe.model.FX;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;

public class TopMenu extends MenuBar {
    ModelManager model;
    Menu save;
    MenuItem mSave;

    public TopMenu(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        mSave.setOnAction(save -> model.save());
    }

    private void createViews() {
        save = new Menu("Save");
        mSave = new MenuItem("save");
        mSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        mSave = new MenuItem("Save");

        save.getItems().add(mSave);

        this.getMenus().addAll(save);

        //this.setUseSystemMenuBar(true);
    }
}
