package classes;

import java.util.*;

public class Algorithms {
    private final  Long goal = 305419896L ;
    public Map<Long, Long> parents;
    public Set<Long> explored;
    private  State state ;

    public Algorithms() {
        state = new State() ;
    }
    private  void swap ( int arr[][] , int i, int j , int x , int y  ){
        int tmp = arr[i][j] ;
        arr[i][j] = arr[x][y] ;
        arr[x][y] = tmp ;
    }
    private Long getMove(int arr[][] , int i , int j , int x , int y ){
        swap( arr , i, j , x, y );
        Long s =   state.setStateLong( state.getStringFromTwoD(arr) )    ;
        swap( arr , i, j , x , y );
        return s ;
    }
    public   ArrayList<Long>  getNeighbors(){
        ArrayList<Long> adj  = new ArrayList<>();
        int[][] arr = state.formatToTwoD() ; // set zeroRow & zeroColumn .
        if (   state.getZeroRow() > 0 ){
            adj.add( getMove(arr , state.getZeroRow() , state.getZeroColumn() , state.getZeroRow() - 1 , state.getZeroColumn() ) )  ;
        }
        if ( state.getZeroColumn() > 0 ){
            adj.add( getMove(arr , state.getZeroRow() , state.getZeroColumn() , state.getZeroRow()  , state.getZeroColumn()  - 1) )  ;
        }
        if ( state.getZeroRow() < 2  ){
            adj.add( getMove(arr , state.getZeroRow() , state.getZeroColumn() , state.getZeroRow() + 1 , state.getZeroColumn() ) )  ;
        }
        if ( state.getZeroColumn() < 2 ){
            adj.add( getMove(arr , state.getZeroRow() , state.getZeroColumn() , state.getZeroRow()  , state.getZeroColumn()  + 1) )  ;
        }
        return  adj ;
    }
    private void dfs(Long s ){
         Stack <Long> st = new Stack<>() ;
         explored.add(s) ;
         st.push(s) ;
         while( !st.empty() ){
             Long x = st.pop() ;
             if( x.equals(goal) ) break;
             state.setCurrentState(x); // update the instance of state.
             ArrayList<Long> adj = getNeighbors() ;
             for(Long next : adj ){
                 if(  !explored.contains(next) ){
                     parents.put(next , x ) ;
                     explored.add(next) ;
                     st.push(next) ;
                 }
             }
         }
    }

    public Long getLong(String state){
        this.state.setStateLong(state);
        return  this.state.getCurrentState() ;
    }
    public Stack<Long> DFS(Long initialState) {
        initializeMapAndSet( initialState );
        dfs(initialState) ;
        return getPath(); 
    }

    // TODO:: Basel
    public Stack<Long> BFS(Long initialState) {
        initializeMapAndSet(initialState);
        // TODO:: Implement BFS algorithm
        return getPath();

    }

    // TODO:: Man3am
    public Stack<Long> AStarManhattanDistance(Long initialState) {
        return Astar(initialState, new ManhattanDistance());

    }

    // TODO:: Man3am
    public Stack<Long> AStarEuclideanDistance(Long initialState) {
        return Astar(initialState, new EuclideanDistance());

    }

    // TODO:: Man3am
    public Stack<Long> Astar(Long initialState, Heuristics heuristics) {
        initializeMapAndSet(initialState);
        // TODO:: Implement Astar algorithm
        /**
         * to calculate the heuristics -> heuristics.call(initialState)
         */
        return getPath();

    }
    private void  initializeMapAndSet(Long initialState){
        // initialize them as every function will rewrite on them
        parents =  new HashMap<Long,Long>();
        explored = new HashSet<Long>();
        parents.put(initialState , null )  ;

    }

    private Stack<Long> getPath() {
        Stack<Long> path  = new Stack<>();
        if ( !parents.containsKey(goal) ) return path ;
         Long state = goal ;
        while(state != null ){
            path.push(state) ;
            state = parents.get(state) ;
        }
        return path ;
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
