package pt.isec.pa.apoio_poe.model.FX.Phase1.TeacherFx;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

public class TeacherList extends ListPane {
    public TeacherList(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Teacher teacher = (Teacher) this.getSelectionModel().getSelectedItem();
                model.remove(teacher.getEmail());
            }
        });
    }

    public Teacher getSelected(){
        return (Teacher) this.getSelectionModel().getSelectedItem();
    }
}
