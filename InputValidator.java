import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class for validating user input.
 * Checks if inputs follow correct format and constraints.
 */
public class InputValidator {

    // Regular expressions for input validation
    private static final Pattern VEHICLE_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,6}$"); // 3-6 alphanumeric characters
    private static final Pattern MODEL_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9 ]+$"); // Only letters, numbers, and spaces
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$"); // Positive number, max 2 decimal places
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$"); // Format: dd/MM/yyyy
    private static final Pattern STATUS_PATTERN = Pattern.compile("^(available|rented)$", Pattern.CASE_INSENSITIVE); // Only "available" or "rented"

    /**
     * Reads and validates a positive integer input.
     *
     * @param scanner Scanner object for reading user input.
     * @param errorMessage Message displayed when input is invalid.
     * @return A valid positive integer.
     * @throws InvalidInputException If input is not a valid positive integer.
     */
    public static int readPositiveInt(Scanner scanner, String errorMessage) throws InvalidInputException {
        if (!scanner.hasNextInt()) { // Check if input is an integer
            scanner.next(); // Consume invalid input
            throw new InvalidInputException("Invalid input. " + errorMessage);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        if (value <= 0) { // Ensure value is positive
            throw new InvalidInputException("Invalid input. " + errorMessage);
        }
        return value; // Return valid integer
    }

    /**
     * Reads and validates a positive double input.
     *
     * @param scanner Scanner object for reading user input.
     * @param errorMessage Message displayed when input is invalid.
     * @return A valid positive double.
     * @throws InvalidInputException If input is not a valid positive double.
     */
    public static double readPositiveDouble(Scanner scanner, String errorMessage) throws InvalidInputException {
        if (!scanner.hasNextDouble()) {
            scanner.next(); // Consume invalid input
            throw new InvalidInputException("Invalid input. " + errorMessage);
        }
        double value = scanner.nextDouble(); // Check if input is a double
        scanner.nextLine(); // Clear buffer
        if (value <= 0) { // Ensure value is positive
            throw new InvalidInputException("Invalid input. " + errorMessage);
        }
        return value; // Return valid double
    }

    /**
     * Checks if a vehicle ID already exists in the list.
     * @param vehicleId The ID to check.
     * @param vehicles List of existing vehicles.
     * @throws InvalidInputException If the ID already exists.
     */
    public static void validateUniqueVehicleId(String vehicleId, ArrayList<Vehicle> vehicles) throws InvalidInputException {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(vehicleId)) {
                throw new InvalidInputException("Error: Vehicle with ID " + vehicleId + " already exists.");
            }
        }
    }

    /**
     * Validates a vehicle ID (3-6 alphanumeric characters).
     *
     * @param vehicleId The vehicle ID to validate.
     * @throws InvalidInputException If the vehicle ID is not valid.
     */
    public static void validateVehicleId(String vehicleId) throws InvalidInputException {
        if (!VEHICLE_ID_PATTERN.matcher(vehicleId).matches()) { // Check format
            throw new InvalidInputException("Invalid Vehicle ID. It must be 3-6 alphanumeric characters.");
        }
    }

    /**
     * Validates a vehicle model name (letters, numbers, and spaces only).
     *
     * @param modelName The model name to validate.
     * @throws InvalidInputException If the model name is not valid.
     */
    public static void validateModelName(String modelName) throws InvalidInputException {
        if (!MODEL_NAME_PATTERN.matcher(modelName).matches()) { // Check format
            throw new InvalidInputException("Invalid model name. Only letters, numbers, and spaces are allowed.");
        }
    }

    /**
     * Validates a rental price (positive number with up to 2 decimal places).
     *
     * @param price The price string to validate.
     * @throws InvalidInputException If the price format is incorrect.
     */
    public static void validatePrice(String price) throws InvalidInputException {
        if (!PRICE_PATTERN.matcher(price).matches()) { // Check format
            throw new InvalidInputException("Invalid price. Must be a positive number with up to 2 decimal places.");
        }
    }

    /**
     * Validates a date format (must match dd/MM/yyyy).
     * @param date The date string to validate.
     * @throws InvalidRentalDateException If the date format is incorrect.
     */
    public static void validateDate(String date) throws InvalidRentalDateException {
        if (!DATE_PATTERN.matcher(date).matches()) { // Check format
            throw new InvalidRentalDateException("Invalid date format. Must be in the format dd/MM/yyyy.");
        }
    }

    /**
     * Validates the status of a vehicle (must be "available" or "rented").
     *
     * @param status The status string to validate.
     * @throws InvalidInputException If the status is not valid.
     */
    public static void validateStatus(String status) throws InvalidInputException {
        if (!STATUS_PATTERN.matcher(status).matches()) { // Check format
            throw new InvalidInputException("Invalid status. Must be 'available' or 'rented'.");
        }
    }
}

    /**
     * Exception thrown when user input is invalid.
     */
    class InvalidInputException extends Exception {
        /**
         * Constructor
         *
         * @param message Error message
         */
        public InvalidInputException(String message) {
            super(message); // Call Exception constructor
        }
    }

    /**
     * Exception thrown when vehicle is not found.
     */
    class VehicleNotFoundException extends Exception {
        /**
         * Constructor
         *
         * @param message Error message
         */
        public VehicleNotFoundException(String message) {
            super(message); // Call Exception constructor
        }
    }

    /**
     * Exception thrown when rental dates are invalid.
     */
    class InvalidRentalDateException extends Exception {
        /**
         * Constructor
         *
         * @param message Error message
         */
        public InvalidRentalDateException(String message) {
            super(message); // Call Exception constructor
        }
    }

