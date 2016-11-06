/**
 * IEMLS - Environment.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.map.EnvironmentMap;
import model.object.IObject;
import model.object.agent.Agent;
import rx.observables.JavaFxObservable;

import java.util.Optional;

/**
 * TODO: Implement a Timer, each tick of clock update all environment, with agent algorithms in independents threads.
 * TODO: The Threads could wait for ticks, or define a path to follow(but the main idea is work in real time with
 * TODO: dynamic environments, leaks of resources, agents blocking pass)
 *
 */
public class Environment extends Pane {
    private EnvironmentMap<Agent> environmentMap;
    private double aspect = 0.0;
    /**
     * The zoom is determine by size of agent in pixels
     * if zoom is 10 the agent occupy 10 pixels
     */
    private double zoom = 10.0;


    public Environment() {
        clipDraw();

        environmentMap = new EnvironmentMap();

        setOnMouseClicked(mouseEvent -> {
            getEnvironmentMap().set(
                (int)(mouseEvent.getX() / getZoom()),
                (int)(mouseEvent.getY() / getZoom()),
                new Agent());
            paintEnvironmentMap();
        });

    }

    /**
     * Avoid draw outside of this Pane
     */
    private void clipDraw() {
        Rectangle clipRect = new Rectangle(getWidth(), getHeight());
        JavaFxObservable.fromObservableValue(widthProperty())
            .subscribe(width -> {
                clipRect.setWidth(width.intValue());
                setAspect(getWidth() / getHeight());
            });

        JavaFxObservable.fromObservableValue(heightProperty())
            .subscribe(height -> {
                clipRect.setHeight(height.intValue());
                setAspect(getWidth() / getHeight());
            });

        setClip(clipRect);
    }

    /**
     * Paint EnvironmentMap
     */
    private void paintEnvironmentMap() {
        getChildren().clear();
        for (int i = 0; i < getWidth() / getZoom(); i++) {
            for (int j = 0; j < getHeight() / getZoom(); j++) {
                Optional<Agent> iObjectOptional = getEnvironmentMap().get(i, j);
                if (iObjectOptional.isPresent()) {

                    Circle circle = new Circle(getZoom() / 2);
                    circle.setTranslateX(i * getZoom() + getZoom() / 2);
                    circle.setTranslateY(j * getZoom() + getZoom() / 2);
                    getChildren().add(circle);
                }
            }
        }
    }

    /**
     *
     */
    public EnvironmentMap getEnvironmentMap() {
        return environmentMap;
    }

    public double getAspect() {
        return aspect;
    }

    public void setAspect(double aspect) {
        this.aspect = aspect;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
