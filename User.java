/**
 * Abstract class for system user.
 */
public abstract class User {
    protected String id; // Unique user ID
    protected String name; // User name

    /**
     * Constructor for User
     * @param id The unique identifier for the user
     * @param name The user's name
     */
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get the user ID
     * @return user ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the username
     * @return username
     */
    public String getName() {
        return name;
    }

    /**
     * Abstract method for displaying the user menu.
     */
    public abstract void displayMenu();
}
