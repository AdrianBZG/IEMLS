package model.species;

import controller.ConfigurationController;
import controller.SpeciesController;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import model.object.agent.Agent;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.ErrorView;
import view.SpecieItemView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Specie implements Cloneable {

    private String specieName = new String("");
    private int specieId = -1;

    /**
     * All available algorithms
     */
    private static ArrayList<Specie> species = new ArrayList<>();

    public Specie() {
        this.specieName = new String("No name");
        this.specieId = Specie.getSpecies().size();
        Specie.getSpecies().add(this);
    }

    public Specie(String name) {
        this.specieName = new String(name);
        this.specieId = Specie.getSpecies().size();
        Specie.getSpecies().add(this);
    }

    public static ArrayList<Specie> getSpecies() {
        return species;
    }

    public static void showSpeciesManager() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Species Manager");
        dialog.setHeaderText("You can manage environment species with the following options: \n - create specie, \n - delete specie");
        dialog.getDialogPane().setPrefSize(500, 600);

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("SpeciesConfiguration.fxml"));
            Parent root = fxmlLoader.load();
            dialog.getDialogPane().setContent(root);
            dialog.setResultConverter(dialogButton -> {
                return null;
            });

        } catch (IOException e) {
            new ErrorView("No possible load agent configuration");
            e.printStackTrace();
        }


        Optional<String> result = dialog.showAndWait();
    }

    public static void deleteSpecie(Specie specie) {
        System.out.println("Borro: " + specie.getSpecieName());
        species.remove(specie);
    }

    public String getSpecieName() {
        return specieName;
    }

    public void setSpecieName(String specieName) {
        this.specieName = specieName;
    }

    public int getSpecieId() {
        return specieId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }
}
