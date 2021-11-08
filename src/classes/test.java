package classes;

import java.util.Stack;

public class test {
    public int compareTo(State x , State y ) {
        return Long.compare( y.getCurrentState() , x.getCurrentState() )  ;
    }
    public static void main(String[] args) {
     Algorithms a = new Algorithms();
     State s = new State("125340678") ;
     Stack<Long> path  = new Stack<>();
     path = a.DFS(s.getCurrentState()) ;
     System.out.println(a.explored.size());
     System.out.println(path.size());
     while( !path.empty() ){
         Long x = path.pop() ;
         s.setCurrentState(x) ;
         System.out.println( s.setStateToString() ) ;
       }

    }

}
