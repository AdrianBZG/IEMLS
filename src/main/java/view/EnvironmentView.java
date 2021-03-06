/**
 * IEMLS - EnvironmentView.java 4/11/16
 * <p>
 * Copyright 20XX Eleazar Díaz Delgado. All rights reserved.
 */

package view;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import model.AgentsManager;
import model.algorithms.swarm_aco.AntColony;
import model.map.EnvironmentMap;
import model.map.generator.IGenerator;
import model.object.MapObject;
import model.object.agent.Agent;
import model.object.agent.ExplorerAgent;
import model.object.agent.SwarmAgent;
import rx.observables.JavaFxObservable;
import util.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Control visualization of map model, it let add resources and interact with map.
 */
public class EnvironmentView extends Pane {
    public final double TILE_SIZE = 20; // Normal tile size
    private final double MAX_ZOOM = 3.0;
    private final double MIN_ZOOM = 0.3; // Min Multiplier zoom

    /**
     * Model with map representation
     */
    private EnvironmentMap environmentMap;

    /**
     * Manager with all agents available in map
     */
    private AgentsManager agentsManager;

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
    private Tuple<Double, Double> lastDragPos = new Tuple<>(0.0, 0.0);

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
     * This constructor is used when we load a map from a file
     */
    public EnvironmentView (String fileName) throws IOException {
        createEnvironmentMapByFile(fileName);
        setup();
    }


    public void setEnvironmentMap (EnvironmentMap map) {
        this.environmentMap = map;
    }


    /**
     * Setup map
     */
    private void setup() {
        agentsManager = new AgentsManager();
        agentsManager.tickEv = (agent) -> { paintEnvironmentMap(); };
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
     * Draw objects into map, the passive objects stay in EnvironmentMap but active objects like agents are saved into
     * "Agent Manager".
     */
    private void drawObjects() {
        JavaFxObservable.fromNodeEvents(this, MouseEvent.MOUSE_CLICKED)
                .filter(mouseEvent -> mouseEvent.getButton().equals(MouseButton.PRIMARY))
                .subscribe(mouseEvent -> {
                    getPencil().ifPresent(pencil -> {
                        try {
                            int posX = (int) Math.floor((mouseEvent.getX() + getTranslation().getX() + getTileSize()) / getTileSize());
                            int posY = (int) Math.floor((mouseEvent.getY() + getTranslation().getY() + getTileSize()) / getTileSize());

                            if (pencil instanceof SwarmAgent) {
                                SwarmAgent swarmAgent = (SwarmAgent) pencil.clone();
                                swarmAgent.setPosition(posX, posY);
                                swarmAgent.setMap(getEnvironmentMap());
                                ((AntColony) swarmAgent.getAlgorithm()).setEnvironmentView(this);
                            }
                            else if (pencil instanceof Agent) {
                                Agent agent = (Agent) pencil.clone();
                                agent.setPosition(posX, posY);
                                agent.setMap(getEnvironmentMap());
                                getAgentsManager().getAgents().add(agent);
                            } else {
                                getEnvironmentMap().set(posX, posY, (MapObject) pencil.clone());
                            }
                        } catch (CloneNotSupportedException e) {
                            System.out.println("DrawObjects Object NO CLONEABLE");
                            e.printStackTrace();
                        }
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
                            (int) ((mouseEvent.getX() + getTranslation().getX() + getTileSize()) / getTileSize()),
                            (int) ((mouseEvent.getY() + getTranslation().getY() + getTileSize()) / getTileSize()));
                    paintEnvironmentMap();
                });
    }

    public void paintAgent(Agent agent) {
        Node node = agent.getVisualObject();
        Scale scale = new Scale();
        scale.setX(getZoom());
        scale.setY(getZoom());
        node.getTransforms().add(scale);
        node.setTranslateX(-getTranslation().getX() + agent.getPosition().getX() * getTileSize());
        node.setTranslateY(-getTranslation().getY() + agent.getPosition().getY() * getTileSize());
        getChildren().add(node);
    }

    /**
     * Avoid draw outside of this Pane
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
                            -nodeEvent.getX() + lastDragPos.getX() + getTranslation().getX(),
                            -nodeEvent.getY() + lastDragPos.getY() + getTranslation().getY()));
                    lastDragPos.setFst(nodeEvent.getX());
                    lastDragPos.setSnd(nodeEvent.getY());
                    paintEnvironmentMap();
                });

    }

    /**
     * Paint EnvironmentMap
     * TODO: Improve this loop, only iterate over objects, use a good size of chunk to improve the performance
     */
    public void paintEnvironmentMap() {
        getChildren().clear();

        for (int i = 0; i < getWidth() / getTileSize() + 5; i++) {
            for (int j = 0; j < getHeight() / getTileSize() + 5; j++) {
                Optional<MapObject> iObjectOptional;
                if (getTranslation().getX() >= 0 && getTranslation().getY() >= 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int) (Math.floor(getTranslation().getX() / getTileSize() + i)),
                            (int) (Math.floor(getTranslation().getY() / getTileSize() + j)));
                } else if (getTranslation().getX() < 0 && getTranslation().getY() >= 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int) (Math.ceil(getTranslation().getX() / getTileSize() + i)),
                            (int) (Math.floor(getTranslation().getY() / getTileSize() + j)));
                } else if (getTranslation().getX() >= 0 && getTranslation().getY() < 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int) (Math.floor(getTranslation().getX() / getTileSize() + i)),
                            (int) (Math.ceil(getTranslation().getY() / getTileSize() + j)));
                } else {//if (getTranslation().getX() > 0 && getTranslation().getY() >= 0) {
                    iObjectOptional = getEnvironmentMap().get(
                            (int) (Math.ceil(getTranslation().getX() / getTileSize() + i)),
                            (int) (Math.ceil(getTranslation().getY() / getTileSize() + j)));
                }

                if (iObjectOptional.isPresent()) {
                    paintObject(iObjectOptional.get(), i, j);
                }
            }
        }
        paintAgents();

    }

    /**
     * Paint agents
     */
    public void paintAgents() {
        for (Agent agent : getAgentsManager().getAgents()) {
            Node node = agent.getVisualObject();
            Scale scale = new Scale();
            scale.setX(getZoom());
            scale.setY(getZoom());
            node.getTransforms().add(scale);
            node.setTranslateX(-getTranslation().getX() + agent.getPosition().getX() * getTileSize());
            node.setTranslateY(-getTranslation().getY() + agent.getPosition().getY() * getTileSize());
            getChildren().add(node);
        }
    }

    /**
     * Paint a object given a position
     * @param mapObject
     * @param x
     * @param y
     */
    private void paintObject(MapObject mapObject, double x, double y) {
        Node node = mapObject.getVisualObject();
        Scale scale = new Scale();
        scale.setX(getZoom());
        scale.setY(getZoom());
        node.getTransforms().add(scale);
        node.setTranslateX(x * getTileSize() - (getTranslation().getX()) % getTileSize());
        node.setTranslateY(y * getTileSize() - (getTranslation().getY()) % getTileSize());
        getChildren().add(node);
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
     * Set Pencil
     */
    public void setPencil(MapObject mapObject) {
        pencil = Optional.of(mapObject);
    }

    /**
     * Get EnvironmentMap
     */
    public EnvironmentMap getEnvironmentMap() {
        return environmentMap;
    }

    public void createEnvironmentMapByFile(String fileName) throws IOException {
        if (environmentMap == null)
            environmentMap = new EnvironmentMap(fileName);
    }

    public double getTileSize() {
        return getZoom() * TILE_SIZE;
    }

    public double getZoom() {
        return zoom;
    }

    /**
     * Set zoom over a zone
     *
     * @param zoom
     * @param x
     * @param y
     */
    public void setZoom(double zoom, double x, double y) {
        if (zoom > MAX_ZOOM) {
            this.zoom = MAX_ZOOM;
        } else if (zoom < MIN_ZOOM) {
            this.zoom = MIN_ZOOM;
        } else {
            double xOffset = x / (zoom * TILE_SIZE) * ((zoom - this.zoom) * TILE_SIZE);
            double yOffset = y / (zoom * TILE_SIZE) * ((zoom - this.zoom) * TILE_SIZE);
            // TODO: Adjust the translation on zoom
            if (zoom > 0) {
                setTranslation(new Tuple<>(getTranslation().getX() + xOffset, getTranslation().getY() + yOffset));
            }
            else {
                setTranslation(new Tuple<>(getTranslation().getX() + xOffset, getTranslation().getY() + yOffset));
            }
            this.zoom = zoom;
        }
    }

    public Tuple<Double, Double> getTranslation() {
        return translation;
    }

    public void setTranslation(Tuple<Double, Double> translation) {
        this.translation = translation;
    }

    public AgentsManager getAgentsManager() {
        return agentsManager;
    }

    public void setAgentsManager(AgentsManager agentsManager) {
        this.agentsManager = agentsManager;
    }
}
