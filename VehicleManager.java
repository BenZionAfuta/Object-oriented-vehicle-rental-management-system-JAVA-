import java.io.*;
import java.util.*;

/**
 * Class to Manages all vehicle operations.
 */
class VehicleManager implements VehicleOperations {
    private static final String VEHICLE_FILE = "vehicles.txt"; // File name for storing vehicle data
    private static final String RENTAL_FILE = "rentals.txt"; // File for storing rental history
    private double totalRevenue = 0; // Store accumulated rental revenue
    private ArrayList<Vehicle> vehicles; // List of all vehicles
    private List<Rental> rentalHistory = new ArrayList<>(); // Stores completed rental records

    /**
     * Constructor for VehicleManager.
     * If no data exists, it preloads default vehicles
     */
    public VehicleManager() {
        vehicles = new ArrayList<>(); // Initialize the vehicle list
        rentalHistory = new ArrayList<>(); // Initialize the rental history list
        loadFromFile(); // Attempt to load vehicles from a file
        if (vehicles.isEmpty()) { // If no vehicles were loaded, preload default vehicles
            preloadVehicles();
        }
    }

    /**
     * Preloads default vehicles
     */
    private void preloadVehicles() {
        vehicles.add(new Vehicle("V01", "Audi A1", 2013, 120, "Available"));
        vehicles.add(new Vehicle("V02", "Mercedes GLC", 2015, 150, "Available"));
        vehicles.add(new Vehicle("V03", "BMW X5", 2018, 200, "Available"));
        vehicles.add(new Vehicle("V04", "Toyota Corolla", 2020, 90, "Available"));
        vehicles.add(new Vehicle("V05", "Ford Focus", 2016, 80, "Available"));
        vehicles.add(new Vehicle("V06", "Honda Civic", 2017, 85, "Available"));
        vehicles.add(new Vehicle("V07", "Nissan J32", 2019, 110, "Available"));
        vehicles.add(new Vehicle("V08", "Volkswagen Golf", 2014, 95, "Available"));
        vehicles.add(new Vehicle("V09", "Hyundai Elantra", 2012, 70, "Available"));
        vehicles.add(new Vehicle("V10", "Chevrolet Malibu", 2011, 65, "Available"));
        saveToFile();
    }

    /**
     * Adds a new vehicle to the system
     * @param vehicle The vehicle to add
     */
    @Override
    public void addVehicle(Vehicle vehicle) {
        try {
            InputValidator.validateUniqueVehicleId(vehicle.getId(), vehicles);
            vehicles.add(vehicle); // Add vehicle to list
            System.out.println("Vehicle added successfully.");
            Logger.logAction("Vehicle " + vehicle.getId() + " added: " + vehicle.getModel());
            saveToFile(); // Save updated list to file
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes a vehicle from the system
     *
     * @param id The vehicle ID to remove
     * @throws VehicleNotFoundException If the vehicle ID does not exist
     */
    @Override
    public void removeVehicle(String id) throws VehicleNotFoundException {
        try {
            Vehicle vehicle = findVehicleById(id); // Find vehicle by ID
            vehicles.remove(vehicle); // Remove from list
            System.out.println("Vehicle removed successfully."); // Print success
            Logger.logAction("Vehicle " + id + " removed."); // Log action
            saveToFile(); // Save changes
        } catch (VehicleNotFoundException e) {
            Logger.logError(e.getMessage()); // Log error
            throw e; // Rethrow exception
        }
    }

    /**
     * Updates the rental price of a vehicle
     * @param id The vehicle ID
     * @param price The new rental price
     */
    @Override
    public void updateVehicle(String id, double price) {
        try {
            Vehicle vehicle = findVehicleById(id); // Find vehicle
            vehicle.setRentalPrice(price); // Update price
            System.out.println("Vehicle price updated successfully."); // Print success
            Logger.logAction("Vehicle " + id + " price updated to ₪" + price); // Log update
            saveToFile(); // Save changes
        } catch (VehicleNotFoundException e) {
            System.out.println(e.getMessage()); // Print error
            Logger.logError(e.getMessage()); // Log error
        }
    }

    /**
     * Displays all available vehicles in the system
     * If no vehicles are available, it notifies the user
     */
    @Override
    public void displayAvailableVehicles() {
        System.out.println("\n=== Available Vehicles ==="); // Available vehicles title
        boolean found = false; // Track availability

        for (Vehicle v : vehicles) { // Loop through vehicles
            if (v.getStatus().equals("Available")) { // Check availability
                System.out.println(v); // Display vehicle
                found = true;
            }
        }

        if (!found) { // If no vehicles found
            System.out.println("No available vehicles found.");
        }
    }

    /**
     * Displays all rented vehicles
     * If no vehicles are rented, nothing is displayed
     */
    public void displayRentedVehicles() {
        System.out.println("\n--- Rented Vehicles ---"); // Rented vehicles title
        boolean found = false;
        for (Vehicle v : vehicles) { // Loop through vehicles
            if (v.getStatus().equals("Rented")) { // Check if rented
                System.out.println(v); // Display vehicle
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rented vehicles found.");
        }
    }

    /**
     * Searches and sorts available vehicles
     */
    @Override
    public void searchAndSortVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>(); // List for available vehicles
        for (Vehicle v : vehicles) { // Loop through all vehicles
            if (v.getStatus().equals("Available")) { // Check if available
                availableVehicles.add(v); // Add to list
            }
        }

        if (availableVehicles.isEmpty()) { // If no vehicles found
            System.out.println("No available vehicles found.");
            return;
        }

        Collections.sort(availableVehicles); // Use compareTo method in Vehicle class

        System.out.println("\n=== Available Vehicles (Sorted) ==="); // Display sorted list
        for (Vehicle v : availableVehicles) {
            System.out.println(v);
        }
    }

    /**
     * Finds vehicles manufactured within a certain year range.
     */
    public void findByYear(int start, int end) {
        for (Vehicle v : vehicles) { // Loop through all vehicles
            if (v.getYear() >= start && v.getYear() <= end) { // Check if vehicle is within range
                System.out.println(v); // Print vehicle details
            }
        }
    }

    /**
     * Finds a vehicle by its ID.
     * @param vehicleId The vehicle ID to search for.
     * @return The Vehicle object if found.
     * @throws VehicleNotFoundException If vehicle with the given ID is not found.
     */
    public Vehicle findVehicleById(String vehicleId) throws VehicleNotFoundException {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(vehicleId)) {
                return vehicle;
            }
        }
        throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found.");
    }

    /**
     * Displays the three newest vehicles.
     */
    public void showTop3Newest() {
        List<Vehicle> sortedVehicles = new ArrayList<>(vehicles); // Copy vehicle list
        Vehicle.setSortType(Vehicle.SortType.BY_YEAR); // Set sorting type to year
        sortedVehicles.sort(Collections.reverseOrder()); // Sort using compareTo() in descending order

        for (int i = 0; i < Math.min(3, sortedVehicles.size()); i++) { // Print up to the three newest vehicles
            System.out.println(sortedVehicles.get(i));
        }
    }

    /**
     * Removes vehicles older than 10 years.
     */
    public void removeOld(int currentYear) {
        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle v = iterator.next();
            if ((currentYear - v.getYear()) > 10) { // Check if vehicle is older than 10 years
                iterator.remove();
            }
        }
        saveToFile();
    }

    /**
     * Calculates the number of days between two dates (dd/MM/yyyy).
     * Returns at least 1 day, or 0 if an error occurs.
     */
    public int calculateDays(String startDate, String endDate) {
        try {
            String[] startParts = startDate.split("/"); // Split start date
            String[] endParts = endDate.split("/"); // Split end date

            int startDay = Integer.parseInt(startParts[0]); // Extract start day
            int startMonth = Integer.parseInt(startParts[1]); // Extract start month
            int startYear = Integer.parseInt(startParts[2]); // Extract start year

            int endDay = Integer.parseInt(endParts[0]); // Extract end day
            int endMonth = Integer.parseInt(endParts[1]); // Extract end month
            int endYear = Integer.parseInt(endParts[2]); // Extract end year

            // Convert to rough total days
            int startTotalDays = startYear * 365 + startMonth * 30 + startDay;
            int endTotalDays = endYear * 365 + endMonth * 30 + endDay;

            return Math.max(1, endTotalDays - startTotalDays); // Ensure minimum of 1 day
        } catch (Exception e) {
            System.out.println("Error calculating days: " + e.getMessage()); // Print error
            return 0; // Return 0 if an error occurs
        }
    }

    /**
     * Displays statistics about available and rented vehicles
     */
    public void displayStatistics() {
        int available = 0, rented = 0; // Counters for vehicle status
        double totalPrice = 0, highestPrice = 0; // Track pricing stats
        Vehicle mostExpensiveVehicle = null; // Track most expensive vehicle

        for (Vehicle v : vehicles) { // Loop through vehicles
            if (v.getStatus().equals("Available")) {
                available++; // Count available vehicles
            } else {
                rented++; // Count rented vehicles
            }
            totalPrice += v.getRentalPrice(); // Sum rental prices
            if (v.getRentalPrice() > highestPrice) { // Check for most expensive
                highestPrice = v.getRentalPrice();
                mostExpensiveVehicle = v;
            }
        }
        double averagePrice;
        if (vehicles.isEmpty()) {
            averagePrice = 0;
        } else {
            averagePrice = totalPrice / vehicles.size(); // Calculate average price
        }
        int totalEverRented = rentalHistory.size(); // Count total rentals
        System.out.println("\n=== Vehicle Statistics ==="); // Display stats
        System.out.println("Available Vehicles: " + available);
        System.out.println("Rented Vehicles: " + rented);
        System.out.println("Total Rented Vehicles: " + totalEverRented);
        System.out.println("Average Rental Price: ₪" + averagePrice);
        if (mostExpensiveVehicle != null) {
            System.out.println("Most Expensive Vehicle: " + mostExpensiveVehicle);
        }
    }

    /**
     * Rents a vehicle if available.
     * @param vehicleId The ID of the vehicle to rent.
     * @param userId The ID of the user renting the vehicle.
     * @param startDate The rental start date.
     */
    public void rentVehicle(String vehicleId, String userId, String startDate) {
        try {
            Vehicle vehicle = findVehicleById(vehicleId); // Find vehicle
            if (!vehicle.getStatus().equals("Available")) { // Check status
                System.out.println("Vehicle is not available."); // Print message
                return;
            }
            Rental rental = new Rental(vehicleId, vehicle.getModel(), vehicle.getYear(),
                    vehicle.getRentalPrice(), userId, startDate); // Create rental
            vehicles.set(vehicles.indexOf(vehicle), rental); // Update list
            System.out.println("Vehicle rented successfully."); // Print success
            Logger.logAction("Vehicle rented: " + vehicleId + " by User: " + userId); // Log action
            saveToFile(); // Save changes
        } catch (VehicleNotFoundException e) {
            System.out.println(e.getMessage()); // Print error
            Logger.logError(e.getMessage()); // Log error
        }
    }

    /**
     * Returns a rented vehicle, calculates cost, late fees, and records rental history.
     * @param vehicleId The ID of the vehicle being returned.
     * @param userId The ID of the user returning the vehicle.
     * @param returnDate The date the vehicle is returned.
     */
    public void returnVehicle(String vehicleId, String userId, String returnDate) {
        try {
            Vehicle vehicle = findVehicleById(vehicleId); // Find vehicle
            if (!(vehicle instanceof Rental rental)) { // Check if rented
                System.out.println("Error: Vehicle is not rented."); // Print error
                return;
            }
            if (!rental.getUserId().equals(userId)) { // Verify user
                System.out.println("Error: Vehicle not rented by this user."); // Print error
                return;
            }
            rental.setEndDate(returnDate); // Set end date
            double totalCost = calculateTotalCost(rental,
                    calculateDays(rental.getStartDate(), returnDate)); // Calculate cost
            totalRevenue += totalCost; // Add total cost to total revenue
            rental.setTotalCost(totalCost); // Save total
            rentalHistory.add(rental); // Add to history rental
            vehicles.set(vehicles.indexOf(rental),
                    new Vehicle(rental.getId(), rental.getModel(),
                            rental.getYear(), rental.getRentalPrice(), "Available")); // Update list
            System.out.println("Vehicle returned successfully. Total cost: ₪" + totalCost); // Print success
            Logger.logAction("Vehicle returned: " + vehicleId + ", User: " + userId + ", Cost: ₪" + totalCost); // Log
            saveToFile(); // Save changes
        } catch (VehicleNotFoundException e) {
            System.out.println(e.getMessage()); // Print error
            Logger.logError(e.getMessage()); // Log error
        }
    }

    /** Calculate total rental cost */
    public double calculateTotalCost(Rental rental, int rentalDays) {
        return rentalDays * rental.getRentalPrice();
    }

    /** Calculate late fee if any */
    public double calculateLateFee(int rentalDays) {
        final double LATE_FEE_PER_DAY = 50;
        final int ALLOWED_RENTAL_DAYS = 3;
        int lateDays = Math.max(0, rentalDays - ALLOWED_RENTAL_DAYS);
        return lateDays * LATE_FEE_PER_DAY;
    }

    /**
     * Displays a report showing total earnings from rented vehicles.
     */
    public void displayEarningsReport() {
        if (totalRevenue == 0 && !rentalHistory.isEmpty()) {
            for (Rental rental : rentalHistory) {
                totalRevenue += rental.getTotalCost(); // Calculate total revenue from history
            }
        }
        System.out.println("\n=== Earnings Report ===");
        System.out.println("Total Earnings: ₪" + totalRevenue);
        for (Rental rental : rentalHistory) {
            System.out.println("Rental ID: " + rental.getId() + ", Total Cost: ₪" + rental.getTotalCost());
        }
        Logger.logAction("Displayed earnings report: Total Revenue = ₪" + totalRevenue);
    }

    /**
     * Displays the total rental cost for a specific user.
     * @param userId The ID of the user.
     */
    public void showUserTotalCost(String userId) {
        double totalCost = 0; // Initialize total cost
        for (Rental rental : rentalHistory) { // Loop through rental history
            if (rental.getUserId().equals(userId)) { // Check if rental belongs to the user
                totalCost += rental.getTotalCost(); // Add rental cost to total
            }
        }
        System.out.println("\n=== Total Rental Cost for User ID: " + userId + " ===");
        System.out.println("₪" + totalCost);
    }

    /**
     * Checks and displays vehicles that are currently under maintenance.
     */
    public void checkMaintenance() {
        System.out.println("\n=== Vehicles Under Maintenance ===");
        boolean found = false; // Flag to check if any vehicles are under maintenance
        for (Vehicle v : vehicles) { // Loop through vehicles
            if (v.getStatus().equalsIgnoreCase("Maintenance")) { // Check if vehicle needs maintenance
                System.out.println("Vehicle ID: " + v.getId() + " (" + v.getModel() + ") is under maintenance.");
                found = true; // Mark that at least one vehicle was found
            }
        }
        if (!found) {
            System.out.println("No vehicles are under maintenance."); // Message if no vehicles found
        }
    }

    /**
     * Sends a vehicle to maintenance mode by changing its status.
     * @param id The vehicle ID.
     */
    public void sendToMaintenance(String id) {
        try {
            Vehicle vehicle = findVehicleById(id); // Find vehicle
            vehicle.setStatus("Maintenance"); // Set status
            System.out.println("Vehicle sent to maintenance."); // Print success
            Logger.logAction("Vehicle " + id + " sent to maintenance."); // Log action
            saveToFile(); // Save changes
        } catch (VehicleNotFoundException e) {
            System.out.println(e.getMessage()); // Print error
            Logger.logError(e.getMessage()); // Log error
        }
    }

    /**
     * Restores a vehicle from maintenance, making it available again.
     * @param id The vehicle ID.
     */
    public void restoreVehicle(String id) {
        try {
            Vehicle vehicle = findVehicleById(id); // Find vehicle
            if (!vehicle.getStatus().equals("Maintenance")) { // Check status
                System.out.println("Vehicle is not under maintenance."); // Print message
                return;
            }
            vehicle.setStatus("Available"); // Set status to available
            System.out.println("Vehicle restored from maintenance."); // Print success
            Logger.logAction("Vehicle " + id + " restored."); // Log action
            saveToFile(); // Save changes
        } catch (VehicleNotFoundException e) {
            System.out.println(e.getMessage()); // Print error
            Logger.logError(e.getMessage()); // Log error
        }
    }

    /**
     * Retrieves the list of vehicles managed in the system
     * @return List of vehicles
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Retrieves the list of rental history.
     * @return A list of completed rentals
     */
    public List<Rental> getRentalHistory() {
        return rentalHistory;
    }

    /**
     * Retrieves the total revenue generated from vehicle rentals.
     * @return The total rental revenue as a double.
     */
    public double getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Saves vehicles and rental history to separate files.
     */
    public void saveToFile() {
        // Save vehicles to vehicles.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLE_FILE))) {
            for (Vehicle vehicle : vehicles) {
                writer.println(vehicle.getId() + "," + vehicle.getModel() + "," +
                        vehicle.getYear() + "," + vehicle.getRentalPrice() + "," + vehicle.getStatus());
            }
            Logger.logAction("Vehicle data saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
            Logger.logError("Error saving vehicles: " + e.getMessage());
        }
        // Save rentals to rentals.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter(RENTAL_FILE))) {
            for (Rental rental : rentalHistory) {
                String endDate = rental.getEndDate();
                if (endDate == null) {
                    endDate = "Not returned"; // Store as string if not returned
                }
                String status;
                if (rental.getEndDate() == null) {
                    status = "Active"; // Active if not returned
                } else {
                    status = "Completed"; // Completed if returned
                }
                writer.println(rental.getUserId() + "," + rental.getId() + "," + rental.getModel() + "," +
                        rental.getYear() + "," + rental.getRentalPrice() + "," + rental.getStartDate() + "," +
                        endDate + "," + rental.getTotalCost() + "," + status);
            }
            Logger.logAction("Rental data saved to file."); // Log successful save
        } catch (IOException e) {
            System.out.println("Error saving rentals: " + e.getMessage()); // Print error
            Logger.logError("Error saving rentals: " + e.getMessage()); // Log error
        }
    }

    /**
     * Loads vehicles and rental history from separate files if they exist.
     */
    public void loadFromFile() {
        // Load vehicles from vehicles.txt
        File file = new File(VEHICLE_FILE); // Create file for vehicles
        if (!file.exists()) { // Check if vehicle file exists
            Logger.log("No vehicle data found. Starting with default vehicles."); // Log missing data
            return; // Exit function
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) { // Read each line
                String[] data = scanner.nextLine().split(","); // Split CSV data
                String id = data[0]; // Vehicle ID
                String model = data[1]; // Vehicle model
                int year = Integer.parseInt(data[2]); // Vehicle year
                double price = Double.parseDouble(data[3]); // Rental price
                String status = data[4]; // Vehicle status
                vehicles.add(new Vehicle(id, model, year, price, status)); // Add vehicle to list
            }
            Logger.logAction("Vehicle data loaded from file."); // Log successful data load
        } catch (FileNotFoundException e) { // Handle missing file
            System.out.println("No previous vehicle data found. Starting fresh."); // msg for user
            Logger.logError("Failed to load vehicle data: " + e.getMessage()); // Log error
        }
        // Load rentals from rentals.txt
        File rentalFile = new File(RENTAL_FILE); // Create file for rentals
        if (!rentalFile.exists()) { // Check if rental file exists
            Logger.log("No rental data found. Starting fresh."); // Log missing data
            return; // Exit function
        }
        try (Scanner scanner = new Scanner(rentalFile)) {
            while (scanner.hasNextLine()) { // Read each line from the file
                String line = scanner.nextLine();
                String[] data = line.split(","); // Split CSV data
                if (data.length < 9) {
                    System.out.println("Skipping invalid line in rental file: " + line);
                    Logger.logError("Invalid rental data format: " + line);
                    continue; // Skip
                }
                // Extract rental information from the file
                String userId = data[0]; // Get User ID
                String vehicleId = data[1]; // Get Vehicle ID
                String model = data[2]; // Get Vehicle Model
                int year = Integer.parseInt(data[3]); // Get Vehicle Year
                double rentalPrice = Double.parseDouble(data[4]); // Get Rental Price
                String startDate = data[5]; // Get Rental Start Date
                String endDate = data[6]; // Get End Date
                double totalCost = Double.parseDouble(data[7]); // Load total cost
                String status = data[8];
                // If the vehicle has not been returned yet, set the endDate to null
                if (endDate.equals("Not returned")) {
                    endDate = null;
                }
                // Create a new Rental object with the extracted data
                Rental rental = new Rental(vehicleId, model, year, rentalPrice, userId, startDate);
                rental.setEndDate(endDate); // Set the end date if available
                rental.setTotalCost(totalCost);
                totalRevenue += totalCost; // Accumulate total revenue from loaded rentals
                rentalHistory.add(rental); // Add rental to the rental history list
                if (status.equals("Active") && rental.getEndDate() == null) {
                    vehicles.add(rental);
                }
            }
            Logger.logAction("Rental data loaded from file."); // Log successful data load
        } catch (FileNotFoundException e) {
            System.out.println("No previous rental data found. Starting fresh."); // Notify user
            Logger.logError("Failed to load rental data: " + e.getMessage()); // Log error
        } catch (NumberFormatException e) {
            System.out.println("Error parsing rental file. Check format.");
            Logger.logError("Number format error in rental file: " + e.getMessage());
        }
    }
}
