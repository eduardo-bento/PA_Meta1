package pt.isec.pa.apoio_poe.model.FX.Helper;


import javafx.scene.control.Button;

public class MyButton extends Button {
    public MyButton(String title){
        //this.setStyle("-fx-background-radius: 6;" + "-fx-background-color: #D0C9C0;");
        this.setStyle("-fx-background-color: #D0C9C0;");
        this.setText(title);
        this.setMinWidth(100);
    }
}
