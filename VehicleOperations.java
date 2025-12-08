/**
 * Interface for managing vehicle operations.
 */
public interface VehicleOperations {
    void addVehicle(Vehicle vehicle) throws InvalidInputException; // Add a vehicle
    void removeVehicle(String vehicleId) throws VehicleNotFoundException; // Remove a vehicle
    void updateVehicle(String vehicleId, double price); // Update vehicle price
    void displayAvailableVehicles(); // Display all available vehicles
    void searchAndSortVehicles(); // Search and sort vehicles
}