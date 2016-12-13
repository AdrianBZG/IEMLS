package model.algorithms;

import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.model.Transition;
import es.usc.citius.hipster.model.function.ActionFunction;
import es.usc.citius.hipster.model.function.ActionStateTransitionFunction;
import es.usc.citius.hipster.model.function.CostFunction;
import es.usc.citius.hipster.model.function.HeuristicFunction;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.ProblemBuilder;

import es.usc.citius.hipster.model.function.impl.LazyNodeExpander;
import es.usc.citius.hipster.model.problem.SearchProblem;
import model.map.EnvironmentMap;
import model.object.agent.Agent;
import util.Tuple;

/**
 * Created by eleazardd on 13/12/16.
 * TODO: Implement Function of transition to define the map
 */
public class AStar {

    SearchProblem<Object, Tuple<Integer, Integer>, WeightedNode<Object, Tuple<Integer, Integer>, Double>> buildProblem(EnvironmentMap environmentMap, Agent agent) {

        return ProblemBuilder.create()
                .initialState(new Tuple<Integer, Integer>(0,0))
                .defineProblemWithExplicitActions()
                .useActionFunction(new ActionFunction<Object, Tuple<Integer,Integer>>() {
                    @Override
                    public Iterable<Object> actionsFor(Tuple<Integer, Integer> integerIntegerTuple) {
                        return null;
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
}
