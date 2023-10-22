package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String firstPath, String secondPath, String resultPath) throws ExecutionException, InterruptedException, IOException {
        CompletableFuture<String> firstFile = CompletableFuture.supplyAsync(() -> {
            try {
                return Files.readString(Path.of(firstPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> secondFile = CompletableFuture.supplyAsync(() -> {
            try {
                return Files.readString(Path.of(secondPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return firstFile.thenCombine(secondFile, (first, second) -> {
            String result = first + second;
            try {
                Files.write(Path.of(resultPath), result.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return result;


    }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });


    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN

        // END
    }

    private static Path getFullPath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

}

