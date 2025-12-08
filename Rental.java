/**
 * Class for rented vehicles
 */
public class Rental extends Vehicle {
    private final String userId; // ID of user renting the vehicle
    private double totalCost; // Total cost of the rental
    private final String startDate; // Date when the rental starts
    private String endDate; // Date when the rental ends

    /**
     * Constructor for Rental.
     * Initializes rental details, including user information and rental period.
     * @param id Vehicle ID
     * @param model Vehicle model name
     * @param year Manufacturing year of the vehicle
     * @param rentalPrice Price per rental period
     * @param userId ID of the user renting the vehicle
     * @param startDate Date when the rental begins
     */
    public Rental(String id, String model, int year, double rentalPrice, String userId, String startDate) {
        super(id, model, year, rentalPrice, "Rented"); // Call parent constructor and set status as "Rented"
        this.userId = userId; // Assign user ID
        this.totalCost = 0; // Initialize total rental cost to zero
        this.startDate = startDate; // Assign rental start date
        this.endDate = null; // Default to null until the vehicle is returned
    }

    /**
     * Gets the user ID of the renter.
     * @return The user ID who rented the vehicle.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Retrieves the rental start date.
     * @return The start date of the rental
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Retrieves the rental end date.
     * @return The end date of the rental, or null if not yet returned
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the rental.
     * @param endDate The date when the vehicle is returned
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Sets the total cost of the rental.
     * @param totalCost The total amount to be charged for the rental
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * Returns the total rental cost.
     * @return Total cost of the rental.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Generates a string representation of the rental.
     * Includes vehicle details, rental ID, user ID, start date, and end date (if available).
     * @return A formatted string with rental details
     */
    @Override
    public String toString() {
        // Start with Vehicle details (ID, Model, Year, Price, Status)
        String result = super.getId() + "," + userId + "," + startDate;

        // Check if the vehicle has been returned
        if (endDate != null) {
            result += "," + endDate + ",Returned"; // If returned
        } else {
            result += ",Not returned,Pending"; // If not returned
        }

        return result;
    }
}
