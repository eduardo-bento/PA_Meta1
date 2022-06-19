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
    Menu properties;
    MenuItem save;
    MenuItem load;

    public TopMenu(ModelManager model) {
        this.model = model;
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        save.setOnAction(event -> {
            model.save();
        });
        load.setOnAction(event -> {
            model.load();
        });
    }

    private void createViews() {
        properties = new Menu("Properties");

        save = new MenuItem("Save");
        load = new MenuItem("Load");
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        load.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        properties.getItems().addAll(save,load);

        this.getMenus().addAll(properties);

        //this.setUseSystemMenuBar(true);
    }
}
