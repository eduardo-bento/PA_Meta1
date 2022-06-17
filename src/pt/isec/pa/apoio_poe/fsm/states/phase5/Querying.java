package pt.isec.pa.apoio_poe.fsm.states.phase5;

import pt.isec.pa.apoio_poe.data.Data;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.fsm.ContextAdapter;
import pt.isec.pa.apoio_poe.fsm.EState;
import pt.isec.pa.apoio_poe.model.Data.Teacher.Teacher;

import java.util.List;

public class Querying extends ContextAdapter {
    public Querying(Context context, Data data) {
        super(context, data);
    }

    @Override
    public String getData() {
        return data.getData();
    }

    @Override
    public void exportFile(String filePath) {
        data.exportPhase4(filePath);
    }

    @Override
    public int getNumberDestiny(String type) {
        return data.getNumberDestiny(type);
    }

    @Override
    public List<Integer> getPercentage() {
        return data.getPercentage();
    }

    @Override
    public List<Teacher> top5() {
        return data.getTop5();
    }

    @Override
    public EState getState() {
        return EState.QUERYING_PHASE;
    }
}
