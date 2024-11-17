package Models;

import java.io.Serializable;

/**
 * The {@code Pharmacist} class represents a pharmacist in the hospital management system.
 * It extends the {@code User} class and implements {@code Serializable} to allow data persistence.
 * This class is used to store and manage information specific to a pharmacist.
 */

public class Pharmacist extends User implements Serializable {

    /**
     * Constructs a {@code Pharmacist} object with the specified details.
     *
     * @param userID   the unique identifier for the pharmacist.
     * @param name     the name of the pharmacist.
     * @param password the password for the pharmacist's account.
     * @param role     the role of the user (e.g., "pharmacist").
     * @param gender   the gender of the pharmacist.
     * @param age      the age of the pharmacist.
     */
    
    public Pharmacist(String userID, String name, String password, String salt, String role, String gender, int age) {
        super(userID, name, password, salt, role, gender, age);
    }
}