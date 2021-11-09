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

    private void swap(int arr[][], int i, int j, int x, int y) {
        int tmp = arr[i][j];
        arr[i][j] = arr[x][y];
        arr[x][y] = tmp;
    }

    private Long getMove(int arr[][], int i, int j, int x, int y) {
        swap(arr, i, j, x, y);
        Long s = state.setStateLong(state.getStringFromTwoD(arr));
        swap(arr, i, j, x, y);
        return s;
    }

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

    private void dfs(Long s) {
        Stack<Long> st = new Stack<>();
        Stack<Integer> depths = new Stack<Integer>();
        depths.push(0) ;
        explored.add(s);
        st.push(s);
        while (!st.empty()) {
            Long x = st.pop();
            int depth = depths.pop();
            if (x.equals(goal))
                break;
            state.setCurrentState(x); // update the instance of state.
            ArrayList<Long> adj = getNeighbors();
            for (Long next : adj) {
                if (!explored.contains(next)) {
                    parents.put(next, x);
                    explored.add(next);
                    st.push(next);
                    depths.push(depth + 1) ;
                    maxDepth = Math.max(maxDepth , depth  + 1) ;
                }
            }
        }
    }

    public Long getLong(String state) {
        this.state.setStateLong(state);
        return this.state.getCurrentState();
    }

    public Stack<Long> DFS(Long initialState) {
        initializeMapAndSet(initialState);
        dfs(initialState);
        return getPath();
    }

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
                // Add neighbor states if only not in frontier & explored lists
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
        return AStar(initialState, new ManhattanDistance());

    }

    public Stack<Long> AStarEuclideanDistance(State initialState) {
        return AStar(initialState, new EuclideanDistance());

    }

    public Stack<Long> AStar(State initialState, Heuristics heuristics) {
        initializeMapAndSet(initialState.getCurrentState());
        PriorityQueue<State> frontier = new PriorityQueue<State>(Comparator.comparing(State::getStateCost));
        Map<String, State> auxFrontier = new HashMap<String, State>();
        initialState.setStateCost(0);
        initialState.setPathCost(0);
        frontier.add(initialState);
        auxFrontier.put(initialState.setStateToString(), initialState);
        while (!frontier.isEmpty()) {
            State stateToCheck = frontier.poll();
            explored.add(stateToCheck.getCurrentState());
            if (goal.equals(stateToCheck.getCurrentState())) {
                return getPath();
            }
            state.setCurrentState(stateToCheck.getCurrentState());
            ArrayList<Long> neighborsLong = getNeighbors();
            ArrayList<State> neighbors = new ArrayList<>();
            for (Long x : neighborsLong){
                State temp = new State(x);
                neighbors.add(temp);
            }
            for (State neighbor : neighbors) {
                int g = stateToCheck.getPathCost() + 1;
                double f = heuristics.call(neighbor) + g;
                maxDepth = Math.max(g,maxDepth);
                neighbor.setPathCost(g);
                neighbor.setStateCost(f);
            }
            for (State neighbor : neighbors) {
                if (!frontier.contains(neighbor) && !explored.contains(neighbor.getCurrentState())) {
                    parents.put(neighbor.getCurrentState(), stateToCheck.getCurrentState());
                    frontier.add(neighbor);
                    auxFrontier.put(neighbor.setStateToString(), neighbor);
                } else if (frontier.contains(neighbor)) {
                    State temp = auxFrontier.get(neighbor.setStateToString());
                    if (temp.getStateCost() > neighbor.getStateCost()) {
                        parents.put(temp.getCurrentState(), stateToCheck.getCurrentState());
                        frontier.remove(temp);
                        frontier.add(neighbor);
                    }
                }
            }
        }
        return getPath();
    }

    private void initializeMapAndSet(Long initialState) {
        // initialize them as every function will rewrite on them
        parents = new HashMap<Long, Long>();
        explored = new HashSet<Long>();
        parents.put(initialState, null);
        maxDepth = 0;

    }

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
    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}