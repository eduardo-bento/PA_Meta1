package pt.isec.pa.apoio_poe.model;

import javafx.application.Application;
import pt.isec.pa.apoio_poe.fsm.Context;
import pt.isec.pa.apoio_poe.text.UserInterface;

public class Main {
    public static void main(String[] args) {
        //Context context = new Context();
        //UserInterface ui = new UserInterface(context);
        //ui.start();
        Application.launch(MainJFX.class,args);
    }
}
