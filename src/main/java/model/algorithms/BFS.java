package model.algorithms;

import es.usc.citius.hipster.model.Transition;
import es.usc.citius.hipster.model.function.ActionFunction;
import es.usc.citius.hipster.model.function.ActionStateTransitionFunction;
import es.usc.citius.hipster.model.function.CostFunction;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.ProblemBuilder;
import es.usc.citius.hipster.model.problem.SearchProblem;
import model.map.EnvironmentMap;
import model.object.agent.Agent;
import util.Tuple;

/**
 * Created by rudy on 17/12/16.
 */
public class BFS {
    SearchProblem<Object, Tuple<Integer, Integer>, WeightedNode<Object, Tuple<Integer, Integer>, Double>> buildProblem(EnvironmentMap map, Agent agent) {

        return ProblemBuilder.create()
                .initialState(new Tuple<Integer, Integer>(0,0))
                .defineProblemWithExplicitActions()
                .useActionFunction(new ActionFunction<Object, Tuple<Integer,Integer>>() {
                    @Override
                    public Iterable<Object> actionsFor(Tuple<Integer, Integer> integerIntegerTuple) {
                        //map
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
                .build();
    }
}
