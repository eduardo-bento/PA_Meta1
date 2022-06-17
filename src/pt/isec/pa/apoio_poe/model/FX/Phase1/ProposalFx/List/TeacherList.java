package pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.List;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.FX.Phase1.ProposalFx.ProjectFx;
import pt.isec.pa.apoio_poe.model.Log;

public class TeacherList extends ListView<Teacher> {
    protected ModelManager model;
    protected ProjectFx project;

    public TeacherList(ModelManager model,ProjectFx project){
        this.model = model;
        this.project = project;
        createViews();
        registerHandlers();


    }
    private void createViews(){
        this.setMaxHeight(200);
    }
    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_UPDATE, evt -> update());

        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Teacher teacher = this.getSelectionModel().getSelectedItem();
                project.getTeacherField().setText(teacher.getEmail());
            }
        });
    }

    private void update() {
        this.getItems().clear();
        if (model.getTeachers() != null){
            this.getItems().addAll(model.getTeachers());
        }
    }
}
