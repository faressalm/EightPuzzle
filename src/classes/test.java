package classes;

import java.util.HashSet;

public class test {
    public int compareTo(State x , State y ) {
        return Long.compare( y.getCurrentState() , x.getCurrentState() )  ;
    }
    public static void main(String[] args) {
     Algorithms a = new Algorithms();
     State s = new State("123456780") ;
     State x = new State("123456780") ;
     HashSet<State > Explored = new HashSet<State>();
     Explored.add(x) ;
     Explored.add(s) ;
        System.out.println( Explored.size() );
    }

}
