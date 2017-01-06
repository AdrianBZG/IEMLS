package view;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * Created by eleazardd on 3/01/17.
 */
public class ErrorView extends Dialog {

    public ErrorView(String msgError) {
        setTitle("An Error has happened");
        setHeaderText(msgError);
        ButtonType applyChanges = new ButtonType("Got it", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);
        showAndWait();
    }
}

