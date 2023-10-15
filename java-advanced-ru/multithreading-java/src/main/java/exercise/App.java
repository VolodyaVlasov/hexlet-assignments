package exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

class App {
    private static final Logger LOGGER = Logger.getLogger("AppLogger");

    // BEGIN
    public static Map<String, Integer> getMinMax(int[] array) {
        MinThread minThread = new MinThread(array);
        MaxThread maxThread = new MaxThread(array);

        minThread.start();
        LOGGER.info(minThread.getName() + "started");

        maxThread.start();
        LOGGER.info(maxThread.getName() + "started");

        try {
            minThread.join();
            LOGGER.info(minThread.getName() + "finished");
            maxThread.join();
            LOGGER.info(maxThread.getName() + "finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("min", minThread.getResult());
        result.put("max", maxThread.getResult());
        return result;
    }
    // END
}
