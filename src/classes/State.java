package classes;

public class State  {

    private Long currentState;
    private int zeroRow, zeroColumn;
    private double stateCost;// used by A*
    private int pathCost;// used by A*

    public State() {

    }
    public int getZeroRow() {
        return zeroRow;
    }

    public int getZeroColumn() {
        return zeroColumn;
    }

    public State(Long currentState) {
        this.currentState = currentState;
    }
    public State(String currentState) {
        this.currentState = setStateLong(currentState);
    }
    public Long getCurrentState() {
        return currentState;
    }

    public void setCurrentState(long currentState) {
        this.currentState = currentState;
    }

    public Long setStateLong(String state) {
        Long tmpState = 0L ;
        for (int i = 0; i < state.length(); i++) {
            tmpState += (state.charAt(i) - '0');
            tmpState <<= 4;
        }
        tmpState >>= 4;
        return tmpState ;
    }

    public String setStateToString() {
        String state = "";
        long tempCurrentState = currentState;
        int cnt = 0;
        while (cnt != 9) {
            state = "" + (char) ((tempCurrentState & 15) + '0') + state;
            tempCurrentState >>= 4;
            cnt++;
        }
        return state;
    }

    int[][] formatToTwoD() {
        int[][] arr = new int[3][3];
        int idx = 0;
        String state = setStateToString();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = state.charAt(idx++) - '0';
                if (arr[i][j] == 0) {
                    zeroRow = i;
                    zeroColumn = j;
                }
            }
        }
        return arr;
    }

    String getStringFromTwoD(int[][] arr) {
        String state = "";
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                state = state + (char) (arr[i][j] + '0');
        return state;
    }

    public double getStateCost() {
        return stateCost;
    }

    public void setStateCost(double stateCost) {
        this.stateCost = stateCost;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }
}
