/**
 * IEMLS - Resource.java 6/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package model.object;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;
import view.ObjectView.ObjectView;
import view.ObjectView.ResourceView;

/**
 * Represent a unit visual of a resource, but it can be consumed partiality
 */
public class Resource extends MapObject {
    /**
     * Represent a available quantity of resource
     */
    private int quantity;
    /**
     * A identifier of resource, that let to agents determine if useful for them
     */
    private String tag; // TODO: When refactor transform to enum? or it is necessary a explicit name

    /**
     * Create a Resource
     */
    public Resource(int quantity, String name) {
        tag = name;
        this.quantity = quantity;
    }

    /**
     *
     */
    public int getQuantity() {
        return quantity;
    }

    public Resource setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     *
     */
    public String getTag() {
        return tag;
    }

    public Resource setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public TypeObject getType() {
        return TypeObject.Resource;
    }

    @Override
    public ObjectView getVisualObject() {
        return new ResourceView();
    }

    @Override
    public String getName() {
        return "Resource";
    }

    @Override
    public boolean hasOptions() {
        return true;
    }

    /**
     * Display a dialog with capacity of modified the resource settings
     */
    @Override
    public void showOptions() {
        // TODO: Define it
        Dialog dialog = new Dialog();
        dialog.setTitle("Resource Configuration");
        dialog.setHeaderText("You can set quantity, tag of resource");

        FontIcon icon = new FontIcon(FontAwesome.COGS);
        icon.setIconSize(64);
        dialog.setGraphic(icon);

        ButtonType applyChanges = new ButtonType("Apply", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(applyChanges, ButtonType.CANCEL);

        HBox hbox = new HBox();
        Label tagLabel = new Label("Tag:");
        TextField tagInputField = new TextField();
        Label quantityLabel = new Label("Quantity");
        TextField quantityInputField = new TextField();

        hbox.getChildren().addAll(tagLabel, tagInputField, quantityLabel, quantityInputField);
        dialog.getDialogPane().setContent(hbox);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == applyChanges) {
                try {
                    setQuantity(Integer.parseInt(quantityInputField.getText()));
                    setTag(tagInputField.getText());
                }
                catch (Exception e) {
                    System.out.println("Error applying configuration"); // TODO:
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}
