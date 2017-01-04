/**
 * IEMLS - AgentView.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view.ObjectView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
public class AgentView extends ObjectView {

    public AgentView() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.BLUE);
        getChildren().addAll(right);
    }

}
