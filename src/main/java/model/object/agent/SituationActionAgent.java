package model.object.agent;

import controller.SituationActionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import model.algorithms.Algorithm;
import model.algorithms.SituationAction;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.ErrorView;
import view.ObjectView.ObjectView;
import view.ObjectView.SituationActionView;

import java.io.IOException;
import java.util.Optional;

/**
 * TODO: Document it
 * Created by eleazardd on 10/01/17.
 */
public class SituationActionAgent extends Agent {

    public SituationActionAgent() {
        setAlgorithm(new SituationAction());
    }

    public SituationActionAgent(SituationActionAgent situationActionAgent) {
        setAlgorithm(situationActionAgent.getAlgorithm().clone());
    }

    /**
     * Representation of object in 2D dimension
     *
     * @return
     */
    @Override
    public ObjectView getVisualObject() {
        return new SituationActionView();
    }

    /**
     * Name of object to user display identification
     *
     * @return
     */
    @Override
    public String getName() {
        return "Situation-Action Agent";
    }

    /**
     * Is possible configure this object?
     *
     * @return boolean to question
     */
    @Override
    public boolean hasOptions() {
        return true;
    }


    @Override
    public void showOptions() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Perception-Action Configuration");
        dialog.setHeaderText("You can define next action in function of agent perception (up, left, right and down)");
        dialog.getDialogPane().setPrefSize(500, 600);

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);
        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("SituationActionConfiguration.fxml"));
            Parent root = fxmlLoader.load();
            dialog.getDialogPane().setContent(root);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyChanges) {
                    SituationActionController configurationController = fxmlLoader.getController();
                    for (SituationActionController.SituationRule rule : configurationController.tableContent) {
                        ((SituationAction) getAlgorithm()).addRule(rule.getIEval(), rule.getDirection());
                    }
                    return null;
                }
                return null;
            });

        } catch (IOException e) {
            new ErrorView("No possible load agent configuration");
            e.printStackTrace();
        }

        Optional<String> s = dialog.showAndWait();
    }

    public Agent clone() {
        return new SituationActionAgent(this);
    }
}
