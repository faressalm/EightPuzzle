package classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Algorithms {
    private State goal = new State(305419896);
    private Map<State, State> Parents;
    private Set<State> Explored;
    // TODO:: ALi
    public Stack<State> DFS(State initialState) {
        initializeMapAndSet();
        // TODO:: Implement DFS algorithm
        return getPath(); 
    }

    // TODO:: Basel
    public Stack<State> BFS(State initialState) {
        initializeMapAndSet();
        // TODO:: Implement BFS algorithm
        return getPath();

    }

    // TODO:: Man3am
    public Stack<State> AStarManhattanDistance(State initialState) {
        return Astar(initialState, new ManhattanDistance());

    }

    // TODO:: Man3am
    public Stack<State> AStarEuclideanDistance(State initialState) {
        return Astar(initialState, new EuclideanDistance());

    }

    // TODO:: Man3am
    public Stack<State> Astar(State initialState, Heuristics heuristics) {
        initializeMapAndSet();
        // TODO:: Implement Astar algorithm
        /**
         * to calculate the heuristics -> heuristics.call(initialState)
         */
        return getPath();

    }
    private void  initializeMapAndSet(){
        // initialize them as every function will rewrite on them
        Parents =  new HashMap<State,State>();
        Explored = new HashSet<State>();
    }
    // TODO:: ALi
    public Stack<State> getPath() {
        // TODO return stack of parents push the goal and parent of the goal till the initial state
        return null; 

    }

    // TODO:: Man3am
    private class ManhattanDistance implements Heuristics {

        @Override
        public void call(State state) {
            // TODO:: get ManhattanDistance between this sate and goal which defined in the top class
            // TODO :: h = abs(current cell:x - goal:x) + abs(current cell:y - goal:y)

        }

    }
    
    // TODO:: Man3am
    private class EuclideanDistance implements Heuristics {

        @Override
        public void call(State state) {
            // TODO:: get ManhattanDistance between this sate and goal which defined in the top class
            // TODO :: h = sqrt((current cell:x - goal:x)2+ (current cell.y - goal:y)2)

        }
    }

}
