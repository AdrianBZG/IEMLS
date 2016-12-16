/**
 * IEMLS - EnvironmentView.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.map.EnvironmentMap;
import model.map.generator.IGenerator;
import model.map.generator.SimplexNoise;
import model.object.MapObject;
import rx.observables.JavaFxObservable;
import util.Tuple;

import java.util.Optional;

/**
 * TODO: Implement a Timer, each tick of clock update all environment, with agent algorithms in independents threads.
 * TODO: The Threads could wait for ticks, or define a path to follow(but the main idea is work in real time with
 * TODO: dynamic environments, leaks of resources, agents blocking pass)
 *
 */
public class EnvironmentView extends Pane {
    private static double TILE_SIZE = 20; // Normal tile size
    private static double MAX_ZOOM = 3.0;
    private static double MIN_ZOOM = 0.3; // Min Multiplier zoom

    private EnvironmentMap environmentMap;

    /**
     * Determine the size of tiled, by default 1.0x resize the tiled
     */
    private double zoom = 1.0;

    /**
     * Scroll of map on x,y
     */
    private Tuple<Double, Double> translation = new Tuple<>(0.0, 0.0);


    /**
     * Auxiliary click variable
     */
    private Tuple<Double, Double> lastDragPos = new Tuple<>(0.0,0.0);

    /**
     * Paint map object
     */
    private Optional<MapObject> pencil = Optional.empty();

    /**
     * Infinite map
     */
    public EnvironmentView(IGenerator generator) {
        environmentMap = new EnvironmentMap(generator);
        setup();
    }

    /**
     * Generate a basic environment
     */
    public EnvironmentView(int dimX, int dimY, IGenerator generator) {
        environmentMap = new EnvironmentMap(generator);
        environmentMap.setDimensions(dimX, dimY);
        setup();
    }

    /**
     * Setup map
     */
    private void setup() {
        // Event handlers
        clipDraw();
        dragMap();
        drawObjects();
        removeObjects();
        zoomMap();
        redraw();

        // Paint
        paintEnvironmentMap();
    }

    /**
     * Zoom map
     */
    private void zoomMap() {
        JavaFxObservable.fromNodeEvents(this, ScrollEvent.ANY)
            .subscribe(scrollEvent -> {
                System.out.println(scrollEvent.getDeltaY());
                setZoom(scrollEvent.getDeltaY() / 1000 + getZoom(), scrollEvent.getX(), scrollEvent.getY());
                paintEnvironmentMap();
            });
    }

    /**
     * Redraw on changes of window
     */
    private void redraw() {
        JavaFxObservable.fromObservableValue(widthProperty())
                .subscribe(width -> paintEnvironmentMap());

        JavaFxObservable.fromObservableValue(heightProperty())
                .subscribe(height -> paintEnvironmentMap());
    }

    /**
     * Draw Objects
     */
    private void drawObjects() {
        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_CLICKED)
                .filter(mouseEvent -> mouseEvent.getButton().equals(MouseButton.PRIMARY))
                .subscribe(mouseEvent -> {
                    getPencil().ifPresent(pencil -> {
                        getEnvironmentMap().set(
                                (int) ((mouseEvent.getX() + (getTranslation().getX()) + getTileSize()) / getTileSize()),
                                (int) ((mouseEvent.getY() + (getTranslation().getY()) + getTileSize()) / getTileSize()),
                                pencil);
                        paintEnvironmentMap();
                    });
                });

    }

    /**
     * Remove objects from scene
     */
    private void removeObjects() {
        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_CLICKED)
                .filter(nodeEvent -> nodeEvent.getButton().equals(MouseButton.SECONDARY) && nodeEvent.isControlDown())
                .subscribe(mouseEvent -> {
                    getEnvironmentMap().removeAt(
                            (int)((mouseEvent.getX() + getTranslation().getX()) / getTileSize()),
                            (int)((mouseEvent.getY() + getTranslation().getY()) / getTileSize())
                            );
                    paintEnvironmentMap();
                });
    }

    /**
     * Avoid draw outside of this Pane
     * TODO: Change pencil to draw chars
     */
    private void clipDraw() {
        Rectangle clipRect = new Rectangle(getWidth(), getHeight());
        JavaFxObservable.fromObservableValue(widthProperty())
            .subscribe(width -> {
                clipRect.setWidth(width.intValue());
            });

        JavaFxObservable.fromObservableValue(heightProperty())
            .subscribe(height -> {
                clipRect.setHeight(height.intValue());
            });

        setClip(clipRect);
    }

    /**
     * Drag Map
     */
    private void dragMap() {
        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_PRESSED)
            .filter(MouseEvent::isMiddleButtonDown)
            .subscribe(mouseEvent -> {
                lastDragPos.setFst(mouseEvent.getX());
                lastDragPos.setSnd(mouseEvent.getY());
            });

        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_DRAGGED)
            .filter(MouseEvent::isMiddleButtonDown)
            .subscribe(nodeEvent -> {
                setTranslation(new Tuple<>(
                    - nodeEvent.getX() + lastDragPos.getX() + getTranslation().getX(),
                    - nodeEvent.getY() + lastDragPos.getY() + getTranslation().getY()));
                lastDragPos.setFst(nodeEvent.getX());
                lastDragPos.setSnd(nodeEvent.getY());
                paintEnvironmentMap();
            });

    }

    /**
     * Paint EnvironmentMap
     * TODO: Paint object out screen to avoid false white, it happen on translating on borders of map
     * TODO: Adjust the mouse to different values of translation?
     */
    private void paintEnvironmentMap() {
        getChildren().clear();
        for (int i = 0; i < getWidth() / getTileSize(); i++) {
            for (int j = 0; j < getHeight() / getTileSize(); j++) {
                Optional<MapObject> iObjectOptional;
                if (getTranslation().getX() >= 0 && getTranslation().getY() >= 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int)(Math.floor(getTranslation().getX() / getTileSize() + i)),
                            (int)(Math.floor(getTranslation().getY() / getTileSize() + j)));
                }
                else if (getTranslation().getX() < 0 && getTranslation().getY() >= 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int)(Math.ceil(getTranslation().getX() / getTileSize() + i)),
                            (int)(Math.floor(getTranslation().getY() / getTileSize() + j)));
                }
                else if (getTranslation().getX() >= 0 && getTranslation().getY() < 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int)(Math.floor(getTranslation().getX() / getTileSize() + i)),
                            (int)(Math.ceil(getTranslation().getY() / getTileSize() + j)));
                }
                else {//if (getTranslation().getX() > 0 && getTranslation().getY() >= 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int)(Math.ceil(getTranslation().getX() / getTileSize() + i)),
                            (int)(Math.ceil(getTranslation().getY() / getTileSize() + j)));
                }

                if (iObjectOptional.isPresent()) {
                    Node node = iObjectOptional.get().getVisualObject();
                    node.setScaleX(getZoom());
                    node.setScaleY(getZoom());
                    node.setTranslateX(i * getTileSize() - getTranslation().getX() % getTileSize());
                    node.setTranslateY(j * getTileSize() - getTranslation().getY() % getTileSize());
                    getChildren().add(node);
                }
            }
        }
    }

    /**
     * Set Pencil
     */
    public void setPencil(MapObject mapObject) {
        pencil = Optional.of(mapObject);
    }

    /**
     * Set Cursor
     */
    public void setCursor() {
        pencil = Optional.empty();
    }

    public Optional<MapObject> getPencil() {
        return pencil;
    }

    /**
     *
     */
    public EnvironmentMap getEnvironmentMap() {
        return environmentMap;
    }

    public double getTileSize() {
        return getZoom() * TILE_SIZE;
    }

    public double getZoom() {
        return zoom;
    }

    /**
     * Set zoom over a zone
     * @param zoom
     * @param x
     * @param y
     */
    public void setZoom(double zoom, double x, double y) {
        if (zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
        }
        else if (zoom < MIN_ZOOM) {
            this.zoom = MIN_ZOOM;
        }
        else {
            double xOffset = x / (zoom * TILE_SIZE);
            double yOffset = y / (zoom * TILE_SIZE);
            // TODO: Adjust the translation on zoom
            setTranslation(new Tuple<>(getTranslation().getX(), getTranslation().getY()));
            this.zoom = zoom;
        }
    }

    public Tuple<Double, Double> getTranslation() {
        return translation;
    }

    public void setTranslation(Tuple<Double, Double> translation) {
        this.translation = translation;
    }
}
