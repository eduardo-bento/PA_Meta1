package pt.isec.pa.apoio_poe.model.FX.Phase2.List;

import javafx.scene.control.ListView;
import pt.isec.pa.apoio_poe.model.Data.ModelManager;
import pt.isec.pa.apoio_poe.model.Data.Proposals.Proposal;
import pt.isec.pa.apoio_poe.model.Data.Student.Student;
import pt.isec.pa.apoio_poe.model.Log;



public class StudentsWithCandidacy extends ListView<Student> {
    private final ModelManager model;

    public StudentsWithCandidacy(ModelManager model) {
        this.model = model;
        this.setMaxHeight(150);
        registerHandlers();
    }
    private void registerHandlers() {
        model.addPropertyChangeListener(ModelManager.PROP_DATA, evt -> update());
        model.addPropertyChangeListener(ModelManager.PROP_STATE, evt -> update());
    }

    private void update() {
        this.getItems().clear();
        if (model.getStudentsListWithCandidacy() != null){
            this.getItems().addAll(model.getStudentsListWithCandidacy());
        }
    }
}
