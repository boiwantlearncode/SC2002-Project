package Models;

import java.io.Serializable;

/**
 * The {@code Administrator} class represents an administrator user in the system.
 * It extends the {@code User} class and implements {@code Serializable} for data persistence.
 */

public class Administrator extends User implements Serializable {

    /**
     * Constructs a new {@code Administrator} object with the specified details.
     *
     * @param userID   the unique identifier for the administrator.
     * @param name     the name of the administrator.
     * @param password the password for the administrator's account.
     * @param role     the role of the user (e.g., "admin").
     * @param gender   the gender of the administrator.
     * @param age      the age of the administrator.
     */

    public Administrator(String userID, String name, String password, String salt, String role, String gender, int age) {
        super(userID, name, password, salt, role, gender, age);
    }

    /**
     * Default constructor for {@code Administrator}.
     * This is primarily used for scenarios where the administrator's details
     * will be set later.
     */
    
    public Administrator() {}
}