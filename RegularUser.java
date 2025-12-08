import java.util.Scanner;

/**
 * RegularUser Class - Manages renting, returning, and searching vehicles.
 */
class RegularUser extends User {
    private final VehicleManager vehicleManager; // Manages vehicle operations

    /**
     * Constructor for RegularUser
     * @param id User ID
     * @param name User Name
     * @param vehicleManager Handling vehicle operations
     */
    public RegularUser(String id, String name, VehicleManager vehicleManager) {
        super(id, name); // Call parent constructor (User)
        this.vehicleManager = vehicleManager; // Assign vehicle manager
    }

    /**
     * Handles renting a vehicle.
     * @param scanner Scanner for user input.
     */
    public void rentVehicle(Scanner scanner) {
        System.out.print("Enter your User ID: "); // Request User ID from the user
        String userId = scanner.nextLine(); // Read User ID

        System.out.print("Enter Vehicle ID to rent: "); // Request Vehicle ID
        String vehicleId = scanner.nextLine(); // Read Vehicle ID

        System.out.print("Enter rental start date (dd/MM/yyyy): "); // Request rental start date
        String startDate;
        try {
            startDate = scanner.nextLine(); // Read start date
            InputValidator.validateDate(startDate); // Validate date format
        } catch (InvalidRentalDateException  e) {
            System.out.println("Error: " + e.getMessage()); // Print validation error
            return; // Exit function if the date is invalid
        }

        vehicleManager.rentVehicle(vehicleId, userId, startDate); // Call rent function with provided details
    }

    /**
     * Handles returning a rented vehicle.
     * @param scanner Scanner for user input.
     */
    public void returnVehicle(Scanner scanner) {
        System.out.print("Enter your User ID: "); // Request User ID from the user
        String userId = scanner.nextLine(); // Read User ID

        System.out.print("Enter Vehicle ID to return: "); // Request Vehicle ID
        String vehicleId = scanner.nextLine(); // Read Vehicle ID

        System.out.print("Enter return date (dd/MM/yyyy): "); // Request return date
        String returnDate;
        try {
            returnDate = scanner.nextLine(); // Read return date
            InputValidator.validateDate(returnDate); // Validate date format
        } catch (InvalidRentalDateException  e) {
            System.out.println("Error: " + e.getMessage()); // Print validation error
            return; // Exit function if the date is invalid
        }

        vehicleManager.returnVehicle(vehicleId, userId, returnDate); // Call return function with provided details
    }

    /**
     * Allows the user to search and sort vehicles based on a selected option.
     */
    public void searchAndSortVehicles() {
        vehicleManager.searchAndSortVehicles(); // Execute search and sorting operation
    }

    /**
     * Returns the VehicleManager instance associated with the user.
     * @return vehicleManager instance
     */
    public VehicleManager getVehicleManager() {
        return vehicleManager; // Return the assigned vehicle manager
    }

    /**
     * Displays a list of all available vehicles.
     */
    public void displayAvailableVehicles() {
        vehicleManager.displayAvailableVehicles(); // Show available vehicles
    }


    @Override
    public void displayMenu() {
        System.out.println("\n=== Regular User Menu ==="); // Menu header
        System.out.println("1. Rent Vehicle"); // Option to rent a vehicle
        System.out.println("2. Return Vehicle"); // Option to return a rented vehicle
        System.out.println("3. Search and Sort Vehicles"); // Option to search and sort available vehicles
        System.out.println("4. Exit"); // Option to exit the user menu
    }
}
