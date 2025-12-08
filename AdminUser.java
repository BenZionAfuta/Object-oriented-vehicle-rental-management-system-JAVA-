/**
 * AdminUser Class - Manages Vehicles, statistics, and reports.
 */
class AdminUser extends User {
    private final VehicleManager vehicleManager; // Vehicle management

    /**
     * Constructor for AdminUser
     * @param id UserID
     * @param name UserName
     * @param vehicleManager Vehicle Manager instance
     */
    public AdminUser(String id, String name, VehicleManager vehicleManager) {
        super(id, name); // Call User constructor
        this.vehicleManager = vehicleManager; // Assign vehicle manager
    }

    /**
     * Adds a new vehicle
     * @param vehicle The vehicle to add
     */
    public void addVehicle(Vehicle vehicle) {
        try {
            InputValidator.validateVehicleId(vehicle.getId()); // Validate vehicle ID
            InputValidator.validateModelName(vehicle.getModel()); // Validate model name
            InputValidator.validatePrice(String.valueOf(vehicle.getRentalPrice())); // Validate price format
            vehicleManager.addVehicle(vehicle); // Add vehicle to the system
            Logger.logAction(this.getName() + " (ID: " + this.getId() + ") added Vehicle: ID = " + vehicle.getId()); // Log action
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage()); // Print error message if validation fails
        }
    }

    /**
     * Remove a vehicle by ID
     * @param vehicleId Vehicle ID
     */
    public void removeVehicle(String vehicleId) {
        try {
            vehicleManager.removeVehicle(vehicleId);
            Logger.logAction(this.getName() + " (ID: " + this.getId() + ") removed Vehicle: ID = " + vehicleId);
        } catch (VehicleNotFoundException e) {
            Logger.logError(this.getName() + " (ID: " + this.getId() + ") failed to remove Vehicle: ID = " + vehicleId);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update the rental price
     * @param id Vehicle ID
     * @param price New rental price
     */
    public void updateVehicle(String id, double price) {
        vehicleManager.updateVehicle(id, price); // Call method to update vehicle price
    }

    /**
     * Returns the VehicleManager instance
     * @return vehicleManager instance
     */
    public VehicleManager getVehicleManager() {
        return vehicleManager; // Return the assigned vehicle manager
    }

    /**
     * Displays general vehicle statistics
     */
    public void viewStatistics() {
        vehicleManager.displayStatistics(); // Show general statistics
    }

    /**
     * Displays late return fees for all rentals in history.
     */
    private void showLateFees() {
        System.out.println("\n--- Late Return Fees ---"); // Section title
        for (Rental rental : vehicleManager.getRentalHistory()) { // Loop rental history
            int rentalDays = vehicleManager.calculateDays(rental.getStartDate(), rental.getEndDate()); // Calc days
            double lateFee = vehicleManager.calculateLateFee(rentalDays); // Calc fee
            if (lateFee > 0) { // If fee applies
                System.out.println("Rental ID: " + rental.getId() + ", Late Fee: ₪" + lateFee); // Print fee
            }
        }
    }

    /**
     * Generates a report listing all rented vehicles, including active and past rentals.
     */
    public void generateRentalReport() {
        System.out.println("=== Rental Report ==="); // Report header
        vehicleManager.displayRentedVehicles(); // Show currently rented vehicles

        System.out.println("\n--- Rental History ---"); // History title
        for (Rental rental : vehicleManager.getRentalHistory()) { // Show history
            System.out.println(rental);
        }
        showLateFees(); // Show late fees using the shared method
    }

    /**
     * Generates a full report including rental history and earnings
     */
    public void generateReport() {
        generateRentalReport(); // Generate rental report
        vehicleManager.displayEarningsReport(); // Show earnings
        System.out.println("Total Rental Revenue: $" + vehicleManager.getTotalRevenue()); // Show total revenue
    }

    @Override
    public void displayMenu() {
        System.out.println("\n=== Admin Menu ==="); // Print menu title
        System.out.println("1. Manage Vehicles"); // Option to manage vehicles
        System.out.println("2. View Statistics"); // Option to view statistics
        System.out.println("3. Check Maintenance"); // Option to check maintenance status
        System.out.println("4. Generate Report"); // Option to generate reports
        System.out.println("5. View User Rental Cost"); // Option for checking user rental cost
        System.out.println("6. Exit"); // Option to exit menu
    }
}
