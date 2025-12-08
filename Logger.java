import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for handling system logging.
 */
public class Logger {
    private static final String LOG_FILE = "system.log"; // Log file name
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Time format

    /**
     * Logs a message to the log fine with time and date
     * @param message The message to log
     */
    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter); // Get current time
        String logEntry = "[" + timestamp + "] " + message; // Format log entry

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) { // Open file in append mode
            writer.write(logEntry + "\n"); // Write log entry
        } catch (IOException e) {
            System.out.println("Failed to write to log file: " + e.getMessage()); // Deal with write errors
        }
    }

    /**
     * Logs an error message to console and file
     * @param errorMessage Error message to log
     */
    public static void logError(String errorMessage) {
        System.out.println("ERROR: " + errorMessage); // Print error to console
        log("ERROR: " + errorMessage); // Log error to file
    }

    /**
     * Logs an action message to log file
     * @param actionMessage Action message to log
     */
    public static void logAction(String actionMessage) {
        log("ACTION: " + actionMessage); // Log action to file
    }
}
