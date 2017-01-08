/**
 * IEMLS - AgentView.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view.ObjectView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
public class CollectorAgentView extends ObjectView {

    public CollectorAgentView() {
        Circle right = new Circle(10);
        right.setTranslateX(-10);
        right.setTranslateY(-10);
        right.setFill(Color.BLUE);
        Circle littleCircle = new Circle(3);
        littleCircle.setFill(Color.YELLOW);
        littleCircle.setTranslateX(-10);
        littleCircle.setTranslateY(-10);
        getChildren().addAll(right,littleCircle);
    }

}
