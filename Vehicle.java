/**
 * Class for Vehicle system.
 */
public class Vehicle implements Comparable<Vehicle> {
    private final String id; // Unique vehicle ID
    private final String model; // Vehicle model name
    private final int year; // Manufacturing year
    private double rentalPrice; // Price per rental period
    private String status; // Current status (Available, Rented)

    /**
     * Enum for sorting vehicles.
     * Can be sorted by price or by year.
     */
    public enum SortType {
        BY_PRICE, // Sort by rental price
        BY_YEAR   // Sort by manufacturing year
    }

    private static SortType sortType = SortType.BY_PRICE; // Default sorting type

    /**
     * Constructor for Vehicle
     *
     * @param id Vehicle ID
     * @param model Vehicle model
     * @param year Manufacturing year
     * @param rentalPrice Rental price per day
     * @param status Current status (Available, Rented)
     */
    public Vehicle(String id, String model, int year, double rentalPrice, String status) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.rentalPrice = rentalPrice;
        this.status = status;
    }

    /**
     * Gets the vehicle ID
     *
     * @return The vehicle ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the vehicle model
     *
     * @return The vehicle model name
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the manufacturing year
     *
     * @return Vehicle manufacturing year
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the rental price of the vehicle
     *
     * @return The rental price
     */
    public double getRentalPrice() {
        return rentalPrice;
    }

    /**
     * Gets the current status of the vehicle
     *
     * @return The vehicle status (Available, Rented)
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets a new rental price for the vehicle
     *
     * @param rentalPrice The new rental price
     */
    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    /**
     * Updates the status of the vehicle.
     *
     * @param status The new status (Available, Rented)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the sorting type for vehicle comparison.
     * @param type The sorting type (BY_PRICE or BY_YEAR).
     */
    public static void setSortType(SortType type) {
        sortType = type;
    }

    /**
     * Compares this vehicle to another vehicle based on rental price
     * Allows sorting vehicles by price
     *
     * @param other The other vehicle to compare
     * @return Negative value if this vehicle is cheaper
     * 0 if equal
     * Positive value if more expensive
     */
    @Override
    public int compareTo(Vehicle other) {
        if (sortType == SortType.BY_YEAR) {
            return Integer.compare(this.year, other.year); // Compare by year
        }
        return Double.compare(this.rentalPrice, other.rentalPrice); // Compare by rental price
    }

    /**
     * Returns a string representation of the vehicle
     *
     * @return String containing vehicle details
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Model: " + model + ", Year: " + year +
                ", Price: " + rentalPrice + ", Status: " + status;
    }
}
