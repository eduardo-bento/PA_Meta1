package pt.isec.pa.apoio_poe.model.Data.Command;

public interface ICommand {
    boolean execute();
    boolean undo();
}
