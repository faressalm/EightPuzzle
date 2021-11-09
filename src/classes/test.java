package classes;

import java.util.HashSet;
import java.util.Stack;

public class test {
    public static void main(String[] args) {
        State s = new State("410873526");
        State xi = new State("142658730");
        Algorithms a = new Algorithms();
        Stack<Long> path  = new Stack<>();
        path = a.AStarManhattanDistance(xi);
        System.out.println(path.size());
        System.out.println(a.getMaxDepth());
        while( !path.empty() ){
            Long x = path.pop() ;
            s.setCurrentState(x) ;
            System.out.println( s.setStateToString() ) ;
        }
    }
}
