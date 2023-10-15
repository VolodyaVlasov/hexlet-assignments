package exercise;

import java.util.Arrays;

// BEGIN
public class MinThread extends Thread {
    private final int[] array;
    private int result;

    public MinThread(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        result = Arrays.stream(array).min().orElseThrow(() -> new RuntimeException("array is empty"));
    }

    public int getResult() {
        return result;
    }
}
// END
