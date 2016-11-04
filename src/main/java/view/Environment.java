/**
 * IEMLS - Environment.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import rx.observables.JavaFxObservable;

/**
 * TODO: Implement a Timer, each tick of clock update all environment, with agent algorithms in independents threads.
 * TODO: The Threads could wait for ticks, or define a path to follow(but the main idea is work in real time with
 * TODO: dynamic environments, leaks of resources, agents blocking pass)
 *
 */
public class Environment extends Pane {

    public Environment() {
        clipDraw();

        getChildren().add(new Circle(30));

        Circle circle = new Circle(30);
        circle.setTranslateX(600);
        circle.setOnMouseClicked(mouseEvent -> getChildren().remove(circle));
        getChildren().add(circle);

        Agent agent = new Agent();
        agent.setTranslateX(100);
        agent.setTranslateY(100);
        getChildren().add(agent);
    }

    /**
     * Avoid draw outside of this Pane
     */
    private void clipDraw() {
        Rectangle clipRect = new Rectangle(getWidth(), getHeight());
        JavaFxObservable.fromObservableValue(widthProperty())
            .subscribe(width -> clipRect.setWidth(width.intValue()));

        JavaFxObservable.fromObservableValue(heightProperty())
            .subscribe(height -> clipRect.setHeight(height.intValue()));

        setClip(clipRect);
    }
}
