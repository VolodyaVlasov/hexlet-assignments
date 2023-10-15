package exercise;

import java.util.Arrays;

// BEGIN
public class MaxThread extends Thread{

    private final int[] array;
    private int result;

    public MaxThread(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        result = Arrays.stream(array).max().orElseThrow(() -> new RuntimeException("array is empty"));
    }

    public int getResult() {
        return result;
    }
}
// END
