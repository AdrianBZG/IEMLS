/**
 * IEMLS - AgentView.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view.ObjectView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Defines visual aspect of a agent with different showOptions to change in function of type of agent
 *
 * TODO: POSSIBLE CHANGES:
 * TODO: COLOR
 * TODO: SHAPE
 * TODO: IMAGE
 * TODO: SIZE  --  NOT YET
 *
 * TODO: IMPLEMENTS ACTIONS OVER AGENT
 * TODO: SELECT
 * TODO: REMOVE
 *
 */
public class ExplorerAgentView extends ObjectView {

    public ExplorerAgentView() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.BLUE);

        FontIcon icon = new FontIcon(FontAwesome.SEARCH);
        icon.setFill(Color.WHITE);
        icon.setIconSize(16);
        icon.setTranslateX(-16.5);
        icon.setTranslateY(-6);
        getChildren().addAll(right, icon);
    }

}
