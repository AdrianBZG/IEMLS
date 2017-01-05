package model.algorithms;

import model.map.EnvironmentMap;


;

/**
 * Created by eleazardd on 13/12/16.
 * TODO: Implement Function of transition to define the map
 */
public class AStar  {

    private static EnvironmentMap map;
/*
    SearchProblem<Directions, Tuple<Integer, Integer>, WeightedNode<Directions, Tuple<Integer, Integer>, Double>> buildProblem(EnvironmentMap map, Agent agent) {

        this.map = map;
        return ProblemBuilder.create()
                .initialState(new Tuple<Integer, Integer>(0,0))
                .defineProblemWithExplicitActions()
                .useActionFunction(new ActionFunction<Directions, Tuple<Integer,Integer>>() {
                    @Override
                    public Iterable<Directions> actionsFor(Tuple<Integer, Integer> pos) {

                        return  validMovementsFor(pos);

                    }
                })
                .useTransitionFunction(new ActionStateTransitionFunction<util.Directions, Tuple<Integer,Integer>>() {

                    public Tuple<Integer, Integer> apply(Object o, Tuple<Integer, Integer> integerIntegerTuple) {
                        return null;
                    }
                })
                .useCostFunction(new CostFunction<Object, Tuple<Integer,Integer>, Double>() {
                    @Override
                    public Double evaluate(Transition<Object, Tuple<Integer, Integer>> transition) {

                        return null;
                    }
                })
                .useHeuristicFunction(new HeuristicFunction<Tuple<Integer, Integer>, Double>() {
                    @Override
                    public Double estimate(Tuple<Integer, Integer> integerIntegerTuple) {

                        return null;
                    }
                })
                .build();
    }

    public static Iterable<Directions> validMovementsFor(Tuple<Integer, Integer> pos) {

        Tuple<Integer, Integer> upPos = new Tuple<>(pos.getX(), pos.getY() - 1);
        Tuple<Integer, Integer> rightPos = new Tuple<>(pos.getX() + 1, pos.getY());
        Tuple<Integer, Integer> downPos = new Tuple<>(pos.getX(), pos.getY() + 1);
        Tuple<Integer, Integer> leftPos = new Tuple<>(pos.getX() - 1, pos.getY());


        Iterable<Directions> allowed = new ArrayList<>();


        if (map.get(upPos).get().getType() != TypeObject.Obstacle) ((ArrayList<Directions>) allowed).add(Directions.UP);
        if (map.get(rightPos).get().getType() != TypeObject.Obstacle) ((ArrayList<Directions>) allowed).add(Directions.RIGHT);
        if (map.get(downPos).get().getType() != TypeObject.Obstacle) ((ArrayList<Directions>) allowed).add(Directions.DOWN);
        if (map.get(leftPos).get().getType() != TypeObject.Obstacle) ((ArrayList<Directions>) allowed).add(Directions.LEFT);


        return allowed;
    }

    private boolean checkAllowedPos (Tuple<Integer, Integer> nextPos, Agent agent, EnvironmentMap map) {
        return (!agent.getLastPosition().equals(nextPos) &&
                (!map.get(nextPos).isPresent() ||
                        map.get(nextPos).get().getType() != TypeObject.Obstacle));
    }
    */
}
