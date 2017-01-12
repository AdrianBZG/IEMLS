package model.algorithms.swarm_aco;

/**
 * Created by adrian on 10/01/17.
 */

import model.PheromonesManager;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Directions;
import util.Position;
import util.Tuple;
import view.ObjectView.AntView;
import view.ObjectView.ObjectView;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Ant extends Agent {
    static final int WANDER_TIME_TRESH = 500;
    static final double MAX_WANDER_DISTANCE = 5;
    static final double WANDER_CHANCE =.001;
    static final double PHER_THRESH=  .1;
    static final double MAX_ENERGY = 5;
    static final double ENERGY_THRESH = MAX_ENERGY/3;               // ants don't wander in strait lines less than
    static final double ENERGY_LOSS_FACTOR = 240000;				//1/2 energy should be needed to return home

    private Tuple<Integer,Integer> homePoint;
    public boolean hasFood = false;
    public boolean hasStepped = false;

    private int wTimer;
    double energy = MAX_ENERGY;
    public int totalFoodCollected = 0;

    Directions lastDirection = null;

    private enum State {
        Follow, Wander, Home
    }

    private State state = State.Wander;

    public Ant() {
        // 1. Cast the algorithm
        AntColony usedAlgorithm = (AntColony)getAlgorithm();

        // 2. Set the home point for this Ant
        this.homePoint = usedAlgorithm.getHomePosition();
        wTimer = 0;
    }

    public boolean step() {
        if(this.hasStepped){ return false; }

        this.hasStepped =true;
        tryTakeFood(this.getPosition());
        tryLeaveFood(this.getPosition());

        switch (state) {
            case Follow:
                if (Math.random() < WANDER_CHANCE) {
                    this.state = State.Wander;
                    break;
                }

                Tuple<Integer,Integer> bestCell = null;
                double bestDist = -1.0;
                ArrayList<Directions> possibleMovements = this.getAllowedActions();
                for(Directions c : possibleMovements){
                    Tuple<Integer,Integer> possibleMovementPosition;
                    if(c == Directions.UP) {
                        possibleMovementPosition = new Tuple<Integer,Integer>(this.getPosition().getX(), this.getPosition().getY()+1);
                    } else if(c == Directions.DOWN) {
                        possibleMovementPosition = new Tuple<Integer,Integer>(this.getPosition().getX(), this.getPosition().getY()-1);
                    } else if(c == Directions.LEFT) {
                        possibleMovementPosition = new Tuple<Integer,Integer>(this.getPosition().getX()-1, this.getPosition().getY());
                    } else {
                        possibleMovementPosition = new Tuple<Integer,Integer>(this.getPosition().getX()+1, this.getPosition().getY());
                    }

                    if (this.getMap().get(possibleMovementPosition.getX(), possibleMovementPosition.getY()).isPresent()) {
                        if(this.getMap().get(possibleMovementPosition.getX(), possibleMovementPosition.getY()).get().getType().equals(TypeObject.Resource)) {
                            bestCell = possibleMovementPosition;
                            bestDist = 0;
                            break;
                        }
                    }

                    int dr = possibleMovementPosition.getX() - homePoint.getX();
                    int dc = possibleMovementPosition.getY() - homePoint.getY();

                    double dist = Math.pow(dr, 2)+Math.pow(dc, 2);

                    if(dist > bestDist & PheromonesManager.getPheromones().get(possibleMovementPosition) > PHER_THRESH){
                        bestCell = possibleMovementPosition;
                        bestDist = dist;
                    }
                }
                if(bestDist == -1){
                    this.state = State.Wander;
                    break;
                }

                Tuple<Integer,Integer> destPoint = bestCell;
                moveTo(destPoint);
                break;
            case Wander:
                wTimer += 1;
                if(wTimer>WANDER_TIME_TRESH && PheromonesManager.getPheromones().get(this.getPosition()) > PHER_THRESH){
                    this.state = State.Follow;
                    wTimer = 0;
                }
                if (Point2D.distance(this.getPosition().getX(), this.getPosition().getY(), homePoint.getY(), homePoint.getX()) > MAX_WANDER_DISTANCE) {
                    this.state = State.Home;
                    break;
                }
                wander();
                break;
            case Home:
                moveTo(homePoint);
                if(!(this.getPosition().getX() == homePoint.getX() & this.getPosition().getY() == homePoint.getY())){
                    if (hasFood) {
                        double p = PheromonesManager.getPheromones().get(this.getPosition());
                        p += 1/300;
                        PheromonesManager.getPheromonesTimer().put(this.getPosition(), 0.00);
                        PheromonesManager.getPheromonesTimer().put(this.getPosition(), p*1.25);
                        // Dibujar feromona aqui
                    }
                }
                break;
        }

        energy -= 1/ENERGY_LOSS_FACTOR;
        if (energy < ENERGY_THRESH) {
            this.state = State.Home;
        }

        if (this.getPosition().getX() != 0 || this.getPosition().getY() != 0) {
            return true;
        }

        return false;
    }

    private void moveTo(Tuple<Integer,Integer> destination) {
        //1. Calcular ruta a destination si no ha sido calculado con A*
        //2. Moverse 1 paso en la ruta calculada si ya ha sido calculada con A*
    }

    private void wander() {
        // Moverse como el CustomExplorer
        boolean firstStep = lastDirection == null;
        boolean mustChangeAction = false;

        Directions nextAction = null;  // Its possible to don't have any allowed action, so the agent will be still.
        EnvironmentMap map = this.getMap();

        ArrayList<Directions> allowedActions = this.getAllowedActions();
        ArrayList<Directions> resources = new ArrayList<>();


        for (Directions dir : Directions.values())
            // Check if the agent is next to a resource in the given direction.
            map.get(Position.getInDirection(this.getPosition(), dir)).ifPresent(mapObject -> {
                        if (mapObject.getType().equals(TypeObject.Resource) && !this.containsVisitedPoint(Position.getInDirection(this.getPosition(), dir))) {
                            resources.add(dir);
                        }
                    }
            );

        if (resources.size() > 0) { // If there are resources near.
            //System.out.println("Next to resource");
            nextAction = resources.get((int) (Math.random() * resources.size()));
        } else {
            Integer action = (int) (Math.random() * allowedActions.size());
            if (!firstStep && action < ((0.6) * allowedActions.size())) {  // 75 % to take the same action as previous step.
                if (this.checkAllowedPos(Position.getInDirection(this.getPosition(), lastDirection))) {
                    //System.out.println("Same action like before " + lastDirection);
                    nextAction = lastDirection;
                } else {
                    mustChangeAction = true;
                }
            } else {
                mustChangeAction = true;
            }
        }
        if (mustChangeAction) {
            nextAction = allowedActions.get((int) (Math.random() * allowedActions.size()));
        }

        lastDirection = nextAction;
        this.move(nextAction);
    }

    public void tryTakeFood(Tuple<Integer,Integer> position){
        if (this.getMap().get(position.getX(), position.getY()).isPresent()) {
            if(this.getMap().get(position.getX(), position.getY()).get().getType().equals(TypeObject.Resource)) {
                hasFood = true;
                this.state = State.Home;
            }
        }
    }

    public void tryLeaveFood(Tuple<Integer,Integer> position){
        if (state == State.Home && position.getX() == homePoint.getX() && position.getY() == homePoint.getY()) {
            if (hasFood) {
                hasFood = false;
                this.energy = MAX_ENERGY;
                this.totalFoodCollected += 1;
            }
            this.state = State.Follow;
        }
    }

    @Override
    public ObjectView getVisualObject() {
        return new AntView();
    }

    @Override
    public String getName() {
        return "Swarm Ant";
    }

    @Override
    public boolean hasOptions() {
        return false;
    }

    /**
     *
     */
    @Override
    public void showOptions() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Ant();
    }
}

