package model.object.agent;

import controller.SwarmNestConfigurationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.ErrorView;
import view.ObjectView.ObjectView;
import view.ObjectView.SwarmAgentView;

import java.io.IOException;
import java.util.Optional;

public class SwarmAgent extends Agent {


    public SwarmAgent() {
    }

    public SwarmAgent(SwarmAgent swarmAgent) {

    }


    /**
     * Representation of object in 2D dimension
     *
     * @return
     */
    @Override
    public ObjectView getVisualObject() {
        return new SwarmAgentView();
    }

    /**
     * Name of object to user display identification
     *
     * @return
     */
    @Override
    public String getName() {
        return "Swarm Nest";
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
        dialog.setTitle("Swarm Nest Configuration");
        dialog.setHeaderText("You can select the type of swarm algorithm and some properties");
        dialog.getDialogPane().setPrefSize(500, 600);

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);
        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("SwarmNestConfiguration.fxml"));
            Parent root = fxmlLoader.load();
            dialog.getDialogPane().setContent(root);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == applyChanges) {
                    SwarmNestConfigurationController configurationController = fxmlLoader.getController();
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

}
