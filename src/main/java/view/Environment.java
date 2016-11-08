/**
 * IEMLS - Environment.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import model.map.EnvironmentMap;
import model.object.IObject;
import model.object.agent.Agent;
import rx.observables.JavaFxObservable;

import java.util.Optional;

/**
 * TODO: Implement a Timer, each tick of clock update all environment, with agent algorithms in independents threads.
 * TODO: The Threads could wait for ticks, or define a path to follow(but the main idea is work in real time with
 * TODO: dynamic environments, leaks of resources, agents blocking pass)
 * TODO: LIMIT Zoom on getter and setters, 1..100
 *
 */
public class Environment extends Pane {
    private EnvironmentMap<IObject> environmentMap;

    /**
     * TODO: REMOVE NOT USED
     */
    private double aspect = 0.0;
    /**
     * The zoom is determine by size of agent in pixels
     * if zoom is 10 the agent occupy 10 pixels
     */
    private double zoom = 30.0;

    /**
     * Scroll of map on x,y
     */
    private Pair<Double, Double> translation = new Pair<>(0.0, 0.0);


    /**
     * Auxiliary click variable
     */
    private double lastDragPosX = 0.0;
    private double lastDragPosY = 0.0;


    /**
     * Generate a basic environment
     */
    public Environment() {
        environmentMap = new EnvironmentMap();
        clipDraw();
        dragMap();
        drawObjects();
        zoomMap();
    }

    /**
     * Zoom map
     */
    private void zoomMap() {
        JavaFxObservable.fromNodeEvents(this, ScrollEvent.ANY)
            .subscribe(scrollEvent -> {
                setZoom(scrollEvent.getDeltaY() / 10 + getZoom());
                paintEnvironmentMap();
            });
    }

    /**
     * Draw Objects
     */
    private void drawObjects() {
        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_CLICKED)
            .filter(nodeEvent -> nodeEvent.getButton().equals(MouseButton.PRIMARY))
            .subscribe(mouseEvent -> {
                getEnvironmentMap().set(
                    (int)((mouseEvent.getX() + getTranslation().getKey()) / getZoom()),
                    (int)((mouseEvent.getY() + getTranslation().getValue()) / getZoom()),
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
     * Drag Map
     */
    private void dragMap() {
        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_PRESSED)
            .filter(ev -> ev.getButton().equals(MouseButton.MIDDLE))
            .subscribe(mouseEvent -> {
                lastDragPosX = mouseEvent.getX();
                lastDragPosY = mouseEvent.getY();
            });

        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_DRAGGED)
            .filter(nodeEvent -> nodeEvent.getButton().equals(MouseButton.MIDDLE))
            .subscribe(nodeEvent -> {
                setTranslation(new Pair<>(
                    - nodeEvent.getX() + lastDragPosX + getTranslation().getKey(),
                    - nodeEvent.getY() + lastDragPosY + getTranslation().getValue()));
                lastDragPosX = nodeEvent.getX();
                lastDragPosY = nodeEvent.getY();
                paintEnvironmentMap();
            });

    }

    /**
     * Paint EnvironmentMap
     */
    private void paintEnvironmentMap() {
        getChildren().clear();
        for (int i = 0; i < getWidth() / getZoom(); i++) {
            for (int j = 0; j < getHeight() / getZoom(); j++) {
                Optional<Agent> iObjectOptional = getEnvironmentMap().get(
                    (int)(Math.ceil(getTranslation().getKey() / getZoom() + i)),   /// TODO: ceil or floor depends of ???
                    (int)(Math.ceil(getTranslation().getValue() / getZoom() + j)));
                if (iObjectOptional.isPresent()) {
                    Circle circle = new Circle(getZoom() / 2);  /// TODO: REPLACE with pencil object
                    circle.setTranslateX(i * getZoom() + getZoom() / 2 - getTranslation().getKey() % getZoom());
                    circle.setTranslateY(j * getZoom() + getZoom() / 2 - getTranslation().getValue() % getZoom());
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

    public Pair<Double, Double> getTranslation() {
        return translation;
    }

    public void setTranslation(Pair<Double, Double> translation) {
        this.translation = translation;
    }
}
