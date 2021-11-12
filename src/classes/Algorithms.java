package classes;

import java.util.*;

public class Algorithms {
    private final Long goal = 305419896L;
    public Map<Long, Long> parents;
    public Set<Long> explored;
    private State state;
    private int maxDepth;

    public Algorithms() {
        state = new State();
    }
    /**
     * Swap two elements in a 2D array.
     * @param arr 2D array
     * @param i row of the first element
     * @param j column of the first element
     * @param x row of the second element
     * @param y column of the second element
     */
    private void swap(int arr[][], int i, int j, int x, int y) {
        int tmp = arr[i][j];
        arr[i][j] = arr[x][y];
        arr[x][y] = tmp;
    }
     /**
     * Make a transition between 2 States.
     * @param arr 2D array
     * @param i row of the first element to be swapped
     * @param j column of the first element to be swapped
     * @param x row of the second element to be swapped
     * @param y column of the second element to be swapped
     * @return a new state based on the transition made
     */
    private Long getMove(int arr[][], int i, int j, int x, int y) {
        swap(arr, i, j, x, y);
        Long s = state.setStateLong(state.getStringFromTwoD(arr));
        swap(arr, i, j, x, y);
        return s;
    }
     /**
     * Get Neighbors of an empty cell of a state
     * @return array list of these neighbors
     */
    public ArrayList<Long> getNeighbors() {
        ArrayList<Long> adj = new ArrayList<>();
        int[][] arr = state.formatToTwoD(); // set zeroRow & zeroColumn .
        if (state.getZeroRow() > 0) {
            adj.add(getMove(arr, state.getZeroRow(), state.getZeroColumn(), state.getZeroRow() - 1,
                    state.getZeroColumn()));
        }
        if (state.getZeroColumn() > 0) {
            adj.add(getMove(arr, state.getZeroRow(), state.getZeroColumn(), state.getZeroRow(),
                    state.getZeroColumn() - 1));
        }
        if (state.getZeroRow() < 2) {
            adj.add(getMove(arr, state.getZeroRow(), state.getZeroColumn(), state.getZeroRow() + 1,
                    state.getZeroColumn()));
        }
        if (state.getZeroColumn() < 2) {
            adj.add(getMove(arr, state.getZeroRow(), state.getZeroColumn(), state.getZeroRow(),
                    state.getZeroColumn() + 1));
        }
        return adj;
    }
     /**
     * Applying dfs algorithm,
     * @param s the initial state.
     */
    private void dfs(Long s) {
        Stack<Long> st = new Stack<>();
        explored.add(s);
        st.push(s);
        while (!st.empty()) {
            Long x = st.pop();
            if (x.equals(goal))
                break;
            state.setCurrentState(x); // update the instance of state.
            ArrayList<Long> adj = getNeighbors();
            for (Long next : adj) {
                if (!explored.contains(next)) {
                    parents.put(next, x);
                    explored.add(next);
                    st.push(next);
                }
            }
        }
    }
    /**
     * convert a state from string to Long
     * @param state state as string
     * @return state as long
     */
    public Long getLong(String state) {
        this.state.setStateLong(state);
        return this.state.getCurrentState();
    }
     /**
     * Applying DFS algorithm
     * @param initialState
     * @return A stack of the path from initial state to goal(top of the stack)
     */
    public Stack<Long> DFS(Long initialState) {
        initializeMapAndSet(initialState);
        dfs(initialState);
        return getPath();
    }
    /**
     * Applying BFS algorithm
     * @param initialState
     * @return A stack of the path from initial state to goal(top of the stack)
     */
    public Stack<Long> BFS(Long initialState) {
        initializeMapAndSet(initialState);
        // Queue to store frontier
        Queue<Long> frontier = new LinkedList<Long>();
        frontier.add(initialState);
        explored.add(initialState);

        while (!frontier.isEmpty()) {
            Long stateLong = frontier.poll();

            // Stop if goal is reached
            if (stateLong.equals(goal))
                break;

            state.setCurrentState(stateLong); // Update the instance of state
            ArrayList<Long> neighbors = getNeighbors();
            for (Long neighbor : neighbors) {
                // Add neighbor states if only not in explored lists
                if (!explored.contains(neighbor)) {
                    parents.put(neighbor, stateLong);
                    frontier.add(neighbor);
                    explored.add(neighbor);
                }
            }
        }
        return getPath();
    }

    public Stack<Long> AStarManhattanDistance(State initialState) {
        return Astar(initialState, new ManhattanDistance());

    }

    public Stack<Long> AStarEuclideanDistance(State initialState) {
        return Astar(initialState, new EuclideanDistance());

    }

    public Stack<Long> Astar(State initialState, Heuristics heuristics) {
        //initialize maps and queue and other variables
        initializeMapAndSet(initialState.getCurrentState());
        PriorityQueue<State> frontier = new PriorityQueue<State>(Comparator.comparing(State::getStateCost));
        Map<String, State> auxFrontier = new HashMap<String, State>();
        initialState.setStateCost(0);
        initialState.setPathCost(0);

        //insert first element in frontier to start
        frontier.add(initialState);
        auxFrontier.put(initialState.setStateToString(), initialState);
        //loop until there is no elements in frontier
        while (!frontier.isEmpty()) {
            State stateToCheck = frontier.poll(); //get the state with the minimum total cost in the frontier
            auxFrontier.remove(stateToCheck.setStateToString());

            maxDepth = Math.max(stateToCheck.getPathCost(),maxDepth); //update max depth
            explored.add(stateToCheck.getCurrentState());//add the state to the explored list

            if (goal.equals(stateToCheck.getCurrentState())) {
                return getPath();
            }

            //get state children and create their states objects
            state.setCurrentState(stateToCheck.getCurrentState());
            ArrayList<Long> neighborsLong = getNeighbors();
            ArrayList<State> neighbors = new ArrayList<>();
            for (Long x : neighborsLong){
                State temp = new State(x);
                neighbors.add(temp);
            }

            //calculate possible moves cost
            for (State neighbor : neighbors) {
                int g = stateToCheck.getPathCost() + 1;
                double f = heuristics.call(neighbor) + g;
                neighbor.setPathCost(g);
                neighbor.setStateCost(f);
            }

            for (State neighbor : neighbors) {
                //if the state is not in frontier and explored then put it in frontier
                if (!auxFrontier.containsKey(neighbor.setStateToString()) && !explored.contains(neighbor.getCurrentState())) {
                    parents.put(neighbor.getCurrentState(), stateToCheck.getCurrentState()); //to connect states together
                    frontier.add(neighbor);
                    auxFrontier.put(neighbor.setStateToString(), neighbor);
                }

                //if the state is in frontier and have a better cost then update frontier with new cost
                else if (auxFrontier.containsKey(neighbor.setStateToString())) {
                    State temp = auxFrontier.get(neighbor.setStateToString());
                    if (temp.getStateCost() > neighbor.getStateCost()) {
                        parents.put(temp.getCurrentState(), stateToCheck.getCurrentState());
                        frontier.remove(temp);
                        auxFrontier.remove(temp.setStateToString());
                        frontier.add(neighbor);
                        auxFrontier.put(neighbor.setStateToString(),neighbor);
                    }
                }
            }
        }
        return getPath();
    }
   /**
     * Initialize the HashMap and HashSet before applying an algorithm
     * @param initialState
     */
    private void initializeMapAndSet(Long initialState) {
        // initialize them as every function will rewrite on them
        parents = new HashMap<Long, Long>();
        explored = new HashSet<Long>();
        parents.put(initialState, null);
        maxDepth = 0;

    }
   /**
     *
     * @return an empty stack if the problem is not solvable or a stack of the path from initial state to goal(top of the stack)
     */
    private Stack<Long> getPath() {
        Stack<Long> path = new Stack<>();
        if (!parents.containsKey(goal))
            return path;
        Long state = goal;
        while (state != null) {
            path.push(state);
            state = parents.get(state);
        }
        return path;
    }

    private class ManhattanDistance implements Heuristics {
        @Override
        public double call(State state) {
            int[][] goalLocations = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};
            int[][] stateTowD = state.formatToTwoD();
            int totalManhattanDistance = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int tempToCompare = stateTowD[i][j];
                    totalManhattanDistance += Math.abs(i - goalLocations[tempToCompare][0]) + Math.abs(j - goalLocations[tempToCompare][1]);
                }
            }
            return totalManhattanDistance;
        }

    }

    private class EuclideanDistance implements Heuristics {
        @Override
        public double call(State state) {
            int[][] goalLocations = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}, {2, 2}};
            int[][] stateTowD = state.formatToTwoD();
            double totalEuclideanDistance = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int tempToCompare = stateTowD[i][j];
                    totalEuclideanDistance += Math.sqrt(Math.pow(Math.abs(i - goalLocations[tempToCompare][0]), 2) + Math.pow(Math.abs(j - goalLocations[tempToCompare][1]), 2));
                }
            }
            return totalEuclideanDistance;
        }
    }
     /**
     * Return the max Depth of an algorithm is applied
     * @return
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
} 
