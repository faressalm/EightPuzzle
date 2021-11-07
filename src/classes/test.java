package classes;

import java.util.ArrayList;

public class test {

    public static void main(String[] args) {
     Algorithms a = new Algorithms();
     State s = new State("123456780") ;
        ArrayList<State> arr = new ArrayList<>() ;
        arr = a.getNeighbors(s) ;
        for(State x : arr ){
            System.out.println( x.setStateToString() );
        }
    }

}
