package classes;

public class State {

    private long currentState;
    private int zeroRow, zeroColumn;// TODO:: put getter to them 

    public State(long currentState) {
        this.currentState = currentState;
    }
    public long getCurrentState() {
        return currentState;
    }

    public void setCurrentState(long currentState) {
        this.currentState = currentState;
    }

    public void setStateLong(String state) {
        currentState = 0;
        for (int i = 0; i < state.length(); i++) {
            currentState += (state.charAt(i) - '0');
            currentState <<= 4;
        }
        currentState >>= 4;
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
}