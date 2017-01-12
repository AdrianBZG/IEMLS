package model.algorithms.swarm_aco;

/**
 * Created by adrian on 10/01/17.
 */

import model.AgentsManager;
import model.PheromonesManager;
import model.algorithms.AStar.*;
import model.algorithms.AStar.datastructures.*;
import model.map.EnvironmentMap;
import model.object.Pheromone;
import model.object.Resource;
import model.object.TypeObject;
import model.object.agent.Agent;
import model.object.agent.ExplorerAgent;
import util.Directions;
import util.Position;
import util.Tuple;
import view.ObjectView.AntView;
import view.ObjectView.ObjectView;
import view.ObjectView.PheromoneView;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;

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
    private ArrayList<ISearchNode> path = new ArrayList<>();
    // The maximum number of completed nodes. After that number the algorithm returns null.
    // If negative, the search will run until the goal node is found.
    private int maxSteps = -1;
    // Number of search steps the AStar will perform before null is returned.
    private int numSearchSteps;
    public ISearchNode bestNodeAfterSearch;

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

                        // Able to draw the pheromone trail
                        this.getMap().set(this.getPosition().getX(), this.getPosition().getY(), new Pheromone());
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
        // Calcular ruta a destination con RTA*
        GoalPosition goal = new GoalPosition(destination);
        if(goal.isValidPos()) {
            IEMLSSearchNode initialPos = new IEMLSSearchNode(this.getPosition().getX(), this.getPosition().getY(), null, goal, this.getMap(), false);
            path = shortestPath(initialPos, goal);
        }

        Tuple<Integer, Integer> nextPos = ((IEMLSSearchNode) path.get(1)).getPosition();
        Directions dir = Position.getDirectionFromPositions(this.getPosition(), nextPos);
        this.move (dir);
    }

    /**
     * Returns the shortest Path from a start node to an end node according to
     * the A* heuristics (h must not overestimate). initialNode and last found node included.
     */
    public ArrayList<ISearchNode> shortestPath(ISearchNode initialNode, IGoalNode goalNode) {
        //perform search and save the
        ISearchNode endNode = search(initialNode, goalNode);
        if (endNode == null)
            return null;
        //return shortest path according to AStar heuristics
        return path(endNode);
    }

    /**
     * @param initialNode start of the search
     * @param goalNode end of the search
     * @return goal node from which you can reconstruct the path
     */
    public ISearchNode search(ISearchNode initialNode, IGoalNode goalNode) {

        boolean implementsHash = initialNode.keyCode()!=null;
        IOpenSet openSet = new OpenSet(new Ant.SearchNodeComparator());
        openSet.add(initialNode);
        IClosedSet closedSet = implementsHash ? new ClosedSetHash(new Ant.SearchNodeComparator())
                : new ClosedSet(new Ant.SearchNodeComparator());
        // current iteration of the search
        this.numSearchSteps = 0;

        while(openSet.size() > 0 && (maxSteps < 0 || this.numSearchSteps < maxSteps)) {
            //get element with the least sum of costs from the initial node
            //and heuristic costs to the goal
            ISearchNode currentNode = openSet.poll();

            if(goalNode.inGoal(currentNode)) {
                //we know the shortest path to the goal node, done
                this.bestNodeAfterSearch = currentNode;
                return currentNode;
            }
            //get successor nodes
            ArrayList<ISearchNode> successorNodes = currentNode.getSuccessors();
            for(ISearchNode successorNode : successorNodes) {
                boolean inOpenSet;
                if(closedSet.contains(successorNode))
                    continue;
                /* Special rule for nodes that are generated within other nodes:
                 * We need to ensure that we use the node and
                 * its g value from the openSet if its already discovered
                 */
                ISearchNode discSuccessorNode = openSet.getNode(successorNode);
                if(discSuccessorNode != null) {
                    successorNode = discSuccessorNode;
                    inOpenSet = true;
                } else {
                    inOpenSet = false;
                }
                //compute tentativeG
                double tentativeG = currentNode.g() + currentNode.c(successorNode);
                //node was already discovered and this path is worse than the last one
                if(inOpenSet && tentativeG >= successorNode.g())
                    continue;
                successorNode.setParent(currentNode);
                if(inOpenSet) {
                    // if successorNode is already in data structure it has to be inserted again to
                    // regain the order
                    openSet.remove(successorNode);
                    successorNode.setG(tentativeG);
                    openSet.add(successorNode);
                } else {
                    successorNode.setG(tentativeG);
                    openSet.add(successorNode);
                }
            }
            closedSet.add(currentNode);
            this.numSearchSteps += 1;
        }

        this.bestNodeAfterSearch = closedSet.min();
        return null;
    }

    /**
     * returns path from the earliest ancestor to the node in the argument
     * if the parents are set via AStar search, it will return the path found.
     * This is the shortest shortest path, if the heuristic h does not overestimate
     * the true remaining costs.
     * @param node node from which the parents are to be found. Parents of the node should
     *              have been properly set in preprocessing (f.e. AStar.search)
     * @return path to the node in the argument
     */
    public ArrayList<ISearchNode> path(ISearchNode node) {
        ArrayList<ISearchNode> path = new ArrayList<>();
        path.add(node);
        ISearchNode currentNode = node;
        while(currentNode.getParent() != null) {
            ISearchNode parent = currentNode.getParent();
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
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

    static class SearchNodeComparator implements Comparator<ISearchNode> {
        public int compare(ISearchNode node1, ISearchNode node2) {
            return Double.compare(node1.f(), node2.f());
        }
    }
}

