import java.util.Scanner;

/**
 * Manages the main menu and user interactions.
 */
public class MenuManager {
    private final AdminUser admin; // Admin user instance
    private final RegularUser user; // Regular user instance
    private final Scanner scanner; // Scanner for user input

    public MenuManager() {
        VehicleManager vehicleManager = new VehicleManager(); // Initialize vehicle manager
        this.admin = new AdminUser("1", "Admin", vehicleManager); // Create admin user
        this.user = new RegularUser("2", "User", vehicleManager); // Create regular user
        this.scanner = new Scanner(System.in); // Initialize scanner for input
    }

    /**
     * Runs the main menu loop.
     * Allows switching between Admin and User menus, or exiting.
     */
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== Main Menu ==="); // Display menu header
            System.out.println("1. Admin Menu"); // Option for admin menu
            System.out.println("2. User Menu"); // Option for user menu
            System.out.println("3. Exit"); // Option to exit
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt(); // Read user choice
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    adminMenu(); // Open admin menu
                    break;
                case 2:
                    userMenu(); // Open user menu
                    break;
                case 3:
                    isRunning = false; // Exit loop
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
        scanner.close(); // Close scanner when exiting
    }

    /**
     * Runs the admin menu loop.
     * Allows managing vehicles, viewing statistics, checking maintenance, and generating reports.
     */
    private void adminMenu() {
        boolean isAdminRunning = true;
        while (isAdminRunning) {
            admin.displayMenu(); // Display admin menu options
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt(); // Read user input
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageVehiclesMenu(); // Open vehicle management menu
                    break;
                case 2:
                    admin.viewStatistics(); // Display statistics
                    break;
                case 3:
                    maintenanceMenu(); // Open maintenance menu
                    break;
                case 4:
                    admin.generateReport(); // Generate reports
                    break;
                case 5:
                    System.out.print("Enter User ID: "); // Request user ID
                    String userId = scanner.nextLine();
                    admin.getVehicleManager().showUserTotalCost(userId); // Call the function
                    break;
                case 6:
                    isAdminRunning = false; // Exit admin menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
    }

    /**
     * Runs the user menu loop.
     * Allows renting, returning, and searching for vehicles.
     */
    private void userMenu() {
        boolean isUserRunning = true;
        while (isUserRunning) {
            user.displayMenu(); // Display user menu options
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt(); // Read user input
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    user.rentVehicle(scanner); // Rent a vehicle
                    break;
                case 2:
                    user.returnVehicle(scanner); // Return a vehicle
                    break;
                case 3:
                    searchAndSortMenu(); // Open search and sort menu
                    break;
                case 4:
                    isUserRunning = false; // Exit user menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
    }

    /**
     * Manages the admin vehicle operations menu.
     * Allows adding, removing, updating, searching, and maintaining vehicles.
     */
    private void manageVehiclesMenu() {
        boolean isManaging = true; // Controls menu loop
        while (isManaging) {
            System.out.println("\n=== Manage Vehicles ==="); // Menu title
            System.out.println("1. Add Vehicle"); // Option to add vehicle
            System.out.println("2. Remove Vehicle"); // Option to remove vehicle
            System.out.println("3. Update Vehicle Price"); // Option to update vehicle
            System.out.println("4. Search Vehicles"); // Option to search vehicles
            System.out.println("5. Back to Admin Menu"); // Exit option
            System.out.print("Enter your choice: ");

            try {
                int choice = InputValidator.readPositiveInt(scanner, "Please enter a valid menu option."); // Read validated input

                switch (choice) {
                    case 1: // Add vehicle
                        admin.addVehicle(inputVehicle());
                        break;
                    case 2: // Remove vehicle
                        removeVehicleMenu();
                        break;
                    case 3: // Update vehicle price
                        System.out.print("Enter Vehicle ID to update: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter new Rental Price: ");
                        double price = InputValidator.readPositiveDouble(scanner, "Price must be a positive number.");
                        admin.updateVehicle(id, price);
                        break;
                    case 4: // Search vehicles submenu
                        searchVehiclesMenu();
                        break;
                    case 5: // Exit menu
                        isManaging = false;
                        break;
                    default: // Handle invalid input
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InvalidInputException e) { // Handle input errors
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Menu for removing vehicles, allowing removal by ID or clearing old vehicles.
     */
    private void removeVehicleMenu() {
        boolean isRemoving = true; // Loop control
        while (isRemoving) {
            System.out.println("\n=== Remove Vehicle ==="); // Display menu title
            System.out.println("1. Remove by ID"); // Option to Remove specific vehicle
            System.out.println("2. Remove Old Vehicles"); // Option to Remove all old vehicles
            System.out.println("3. Back"); // Option to Return to previous menu
            System.out.print("Enter your choice: ");

            try {
                int choice = InputValidator.readPositiveInt(scanner, "Please enter a valid menu option."); // Read user choice

                switch (choice) {
                    case 1:
                        System.out.print("Enter Vehicle ID to remove: "); // Prompt for vehicle ID
                        String id = scanner.nextLine(); // Read input
                        admin.removeVehicle(id); // Remove vehicle by ID
                        break;
                    case 2:
                        System.out.print("Enter the current year: "); // Ask for current year
                        int currentYear = InputValidator.readPositiveInt(scanner, "Year must be a positive number."); // Read user input
                        admin.getVehicleManager().removeOld(currentYear); // Pass the year to removeOld()
                        System.out.println("Old vehicles removed successfully."); // Confirm removal
                        break;
                    case 3:
                        isRemoving = false; // Exit menu loop
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays the search vehicles submenu.
     * Allows searching for available, rented, and newest vehicles.
     */
    private void searchVehiclesMenu() {
        boolean isSearching = true; // Controls search loop
        while (isSearching) {
            System.out.println("\n=== Search Vehicles ==="); // Submenu title
            System.out.println("1. Show Available Vehicles"); // Option to show all vehicles
            System.out.println("2. Show Rented Vehicles"); // Option to show all rented
            System.out.println("3. Show Top 3 Newest Vehicles"); // Option to show 3 new vehicles
            System.out.println("4. Back to Manage Vehicles Menu"); // Option to Return to previous menu
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            switch (choice) {
                case 1: // Show available vehicles
                    admin.getVehicleManager().displayAvailableVehicles();
                    break;
                case 2: // Show rented vehicles
                    admin.getVehicleManager().displayRentedVehicles();
                    break;
                case 3: // Show top 3 newest vehicles
                    admin.getVehicleManager().showTop3Newest();
                    break;
                case 4: // Exit search menu
                    isSearching = false;
                    break;
                default: // Handle invalid input
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Manages maintenance operations: viewing, adding, and restoring vehicles.
     */
    private void maintenanceMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== Manage Maintenance ==="); // Maintenance title
            System.out.println("1. View Vehicles Under Maintenance"); // Option to view under Maintenance
            System.out.println("2. Send Vehicle to Maintenance"); // Option to send to Maintenance
            System.out.println("3. Restore Vehicle from Maintenance"); // Option to restore from Maintenance
            System.out.println("4. Back to Admin Menu"); // Option to Return to previous menu
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear input buffer

            switch (choice) {
                case 1: // Show vehicles under maintenance
                    admin.getVehicleManager().checkMaintenance();
                    break;
                case 2: // Send vehicle to maintenance
                    System.out.print("Enter Vehicle ID to send to maintenance: ");
                    String maintenanceId = scanner.nextLine();
                    admin.getVehicleManager().sendToMaintenance(maintenanceId);
                    break;
                case 3: // Restore vehicle from maintenance
                    System.out.print("Enter Vehicle ID to restore from maintenance: ");
                    String restoreId = scanner.nextLine();
                    admin.getVehicleManager().restoreVehicle(restoreId);
                    break;
                case 4: // Exit maintenance menu
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the search and sort menu for vehicles.
     * Allows users to view, sort, and search for vehicles based on different criteria.
     */
    private void searchAndSortMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Search and Sort Vehicles ==="); // Menu header
            System.out.println("1. Show Available Vehicles"); // Option to display available vehicles
            System.out.println("2. Sort by year"); // Option to sort vehicles by year
            System.out.println("3. Sort by price"); // Option to sort vehicles by price
            System.out.println("4. Search by Year Range"); // Option to search vehicles by year range
            System.out.println("5. Back to User Menu"); // Option to return to user menu
            System.out.print("Enter your choice: ");

            int sortChoice = scanner.nextInt(); // Read user choice
            scanner.nextLine(); // Clear input buffer

            switch (sortChoice) {
                case 1:
                    user.displayAvailableVehicles(); // Show all available vehicles
                    break;
                case 2:
                    Vehicle.setSortType(Vehicle.SortType.BY_YEAR); // Set sorting method to year
                    user.searchAndSortVehicles(); // Sort vehicles by year
                    break;
                case 3:
                    Vehicle.setSortType(Vehicle.SortType.BY_PRICE); // Set sorting method to price
                    user.searchAndSortVehicles(); // Sort vehicles by price
                    break;
                case 4:
                    System.out.print("Enter start year: "); // Prompt for start year
                    int startYear = scanner.nextInt(); // Read start year
                    System.out.print("Enter end year: "); // Prompt for end year
                    int endYear = scanner.nextInt(); // Read end year
                    scanner.nextLine(); // Clean buffer
                    user.getVehicleManager().findByYear(startYear, endYear); // Search vehicles by year range
                    break;
                case 5:
                    running = false; // Exit the search and sort menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Handle invalid input
            }
        }
    }

    /**
     * Collects input from the user to create a new vehicle.
     * Ensures valid input using InputValidator.
     * @return A new Vehicle object with user-provided details.
     */
    private Vehicle inputVehicle() {
        System.out.print("Enter Vehicle ID: ");
        String id = scanner.nextLine(); // Read vehicle ID
        System.out.print("Enter Vehicle Model: ");
        String model = scanner.nextLine(); // Read vehicle model

        int year = 0; // Initialize vehicle year
        double price = 0; // Initialize rental price

        // Validate vehicle year input
        while (true) {
            try {
                System.out.print("Enter Vehicle Year: ");
                year = InputValidator.readPositiveInt(scanner, "Year must be a positive number."); // Read and validate year
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage()); // Display error message
            }
        }

        // Validate rental price input
        while (true) {
            try {
                System.out.print("Enter Rental Price: ");
                price = InputValidator.readPositiveDouble(scanner, "Price must be a positive number."); // Read and validate price
                break;
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage()); // Display error message
            }
        }

        return new Vehicle(id, model, year, price, "Available"); // Return a new vehicle object
    }
}
