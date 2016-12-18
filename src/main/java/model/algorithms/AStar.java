package model.algorithms;

import es.usc.citius.hipster.model.Transition;
import es.usc.citius.hipster.model.function.ActionFunction;
import es.usc.citius.hipster.model.function.ActionStateTransitionFunction;
import es.usc.citius.hipster.model.function.CostFunction;
import es.usc.citius.hipster.model.function.HeuristicFunction;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.ProblemBuilder;

import es.usc.citius.hipster.model.problem.SearchProblem;
import model.map.EnvironmentMap;
import model.object.TypeObject;
import model.object.agent.Agent;
import util.Directions;
import util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by eleazardd on 13/12/16.
 * TODO: Implement Function of transition to define the map
 */
public class AStar  {

    private static EnvironmentMap map;

    SearchProblem<Object, Tuple<Integer, Integer>, WeightedNode<Object, Tuple<Integer, Integer>, Double>> buildProblem(EnvironmentMap map, Agent agent) {

        this.map = map;
        return ProblemBuilder.create()
                .initialState(new Tuple<Integer, Integer>(0,0))
                .defineProblemWithExplicitActions()
                .useActionFunction(new ActionFunction<Object, Tuple<Integer,Integer>>() {
                    @Override
                    public Iterable<Directions> actionsFor(Tuple<Integer, Integer> pos) {

                        return  validMovementsFor(pos);

                    }
                })
                .useTransitionFunction(new ActionStateTransitionFunction<Object, Tuple<Integer,Integer>>() {
                    @Override
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
        return (!agent.getLastPos().equals(nextPos) &&
                (!map.get(nextPos).isPresent() ||
                        map.get(nextPos).get().getType() != TypeObject.Obstacle));
    }
}
