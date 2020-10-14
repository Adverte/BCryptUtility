/**
 * @author Oleg Ushakov. 2020
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class BCryptUtility {

    private final static String RESULT_FILE = "result.csv";
    private final static String SOURCE_FILE = "source.csv";
    public static final String COMMA_DELIMITER = ";";

    public static void main(String[] args) throws IOException {
//        String password = "YourPassword"; //for example "123456"
//        String encrytedPassword = encrytePassword(password);
//        System.out.println("Encryted Password: " + encrytedPassword);

        Path path = Paths.get(SOURCE_FILE);
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(e -> {
                String[] values = e.split(COMMA_DELIMITER);
                values[1] = encrytePassword(values[1]);
                return String.join(COMMA_DELIMITER, values);
            })
                .forEach(e -> writeToFile.accept(RESULT_FILE, e));
        }
        System.out.println("Completed");
    }

    static BiConsumer<String, String> writeToFile = (path, string) -> {
        try {
            Files.write(Paths.get(path), Collections.singletonList(string), StandardCharsets.UTF_8,
                StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    // Encryte Password with BCryptPasswordEncoder
    static String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
