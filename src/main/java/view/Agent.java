/**
 * IEMLS - Agent.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Defines visual aspect of a agent with different options to change in function of type of agent
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
public class Agent extends Group {

    public Agent() {
        Circle left = new Circle(30);
        Circle right = new Circle(30);
        right.setTranslateX(100);
        getChildren().addAll(left, right);

        setOnMouseClicked(mouseEvent -> {
            left.setFill(Color.BLUE);
            right.setFill(Color.BLUE);
        });
    }
}
