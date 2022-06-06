package pt.isec.pa.apoio_poe.model.FX.Phase1.StudentFx;

import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.FX.ListPane;

public class StudentList extends ListPane {
    public StudentList(ModelManager model) {
        super(model);
        registerHandlers();
    }

    private void registerHandlers() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2){
                Student student = (Student) this.getSelectionModel().getSelectedItem();
                model.remove(student.getId());
            }
        });
    }
}
