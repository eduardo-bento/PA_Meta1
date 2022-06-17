package pt.isec.pa.apoio_poe.model.Data.Command;

import java.util.ArrayDeque;
import java.util.Deque;

public class Invoker {
    private Deque<ICommand> history;
    private Deque<ICommand> redo;

    public Invoker(){
        history = new ArrayDeque<>();
        redo = new ArrayDeque<>();
    }

    public boolean invokeCommand(ICommand command){
        if (command.execute()){
            history.push(command);
            return true;
        }
        history.clear();
        return false;
    }

    public boolean undo(){
        if (history.isEmpty())
            return false;

        ICommand command = history.pop();
        command.undo();
        redo.push(command);
        return true;
    }

    public boolean redo(){
        if (redo.isEmpty())
            return false;

        ICommand command = redo.pop();
        command.execute();
        history.push(command);
        return true;
    }

    public boolean hasUndo(){
        return !history.isEmpty();
    }

    public boolean hasRedo(){
        return !redo.isEmpty();
    }
}
