package classes;

public class State {
    static long goal = 305419896;
    
    private long currentState;
    public State(long currentState) {
        this.currentState = currentState;
    }


    public long getCurrentState() {
        return currentState;
    }
    public void setCurrentState(long currentState) {
        this.currentState = currentState;
    }
    
    
}
