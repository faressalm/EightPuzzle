package classes;

public class State {
    // static long goal = 305419896;
    
    private long currentState;
    private int x , y ;
    public State(long currentState) {
        this.currentState = currentState;
    }
    public State(){
    }
    /*
    0,1,2,3,4,5,6,7,8
     */
    /*
   0 1 2    1 0 2
   3 4 5 -> 3 4 5
   6 7 8    6 7 8
    */

    public long getCurrentState() {
        return currentState;
    }
    public void setCurrentState(long currentState) {
        this.currentState = currentState;
    }
    public void setStateLong(String state){
        currentState = 0 ;
        for(int i = 0 ; i < state.length() - 1 ; i ++ ){
            currentState += (state.charAt(i) - '0') ;
            currentState <<= 4 ;
        }
        currentState += (state.charAt(state.length() - 1) - '0') ;
    }
    public String getStateString( ){
       String state = "" ;
       long x = currentState ;
       int cnt = 0 ;
       while( cnt != 9 ){
           state = "" +  (char)(  (x & 15)  + '0') + state  ;
           x>>= 4 ;
           cnt++ ;
       }
       return  state ;
    }
    int [][] get2D(){
        int[][] arr = new int[3][3] ;
        int idx = 0 ;
        String state = getStateString() ;
        for(int i = 0 ; i < 3 ; i ++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = state.charAt(idx++) - '0';
               if ( arr[i][j] == 0 ) {
                   x = i;
                   y = j;
               }
            }
        }
        return arr;
    }
    String  getStringForm2D(int[][] arr){
        String x = "" ;
        for(int i = 0 ; i < 3 ; i ++ )
            for(int j = 0 ; j < 3 ; j ++ )
                 x = x + (char)(arr[i][j] + '0') ;
      return x ;
    }

    
}
