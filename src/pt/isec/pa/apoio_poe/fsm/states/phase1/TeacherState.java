package pt.isec.pa.apoio_poe.fsm.states.phase1;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Teacher;

public class TeacherState extends ContextAdapter {
    public TeacherState(Context context, Data data) {
        super(context, data);
    }

    @Override
    public boolean insert(Object item) {
        return data.insert(item);
    }

    @Override
    public <T> boolean remove(T id) {
        return data.remove(id, Teacher.class);
    }

    @Override
    public void readFromFile(String filePath) {
        data.readCSV(filePath,Teacher.class);
    }

    @Override
    public <T, K, A> boolean edit(T id, K value, String label, Class<A> type) {
        return data.edit(id,value,label,Teacher.class);
    }

    @Override
    public EState getState() {
        return EState.TEACHER;
    }
}
