package pt.isec.pa.apoio_poe.model.FX.ProposalFx;

import javafx.scene.control.TextField;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Project;

public class ProjectFx extends P{
    private final TextField teacherField = new TextField();
    public ProjectFx(ModelManager model) {
        super(model);
        createViews();
        registerHandlers();
        update();
    }

    private void update() {
    }

    private void registerHandlers() {
        insert.setOnAction(event -> {
            model.insert(new Project(idField.getText(),titleField.getText(),2,null,null));
        });
    }

    private void createViews() {
        teacherField.setPromptText("Enter your teacher.");
        this.getChildren().addAll(teacherField);
    }
}
