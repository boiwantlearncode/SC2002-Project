package Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The {@code Doctor} class represents a doctor in the hospital management system.
 * It extends the {@code User} class and includes additional fields for specialization
 * and availability.
 * This class implements {@code Serializable} for data persistence.
 */

public class Doctor extends User implements Serializable {

    /**
     * The specialization of the doctor (e.g., "Cardiology", "General Practitioner").
     */

    private String specialization;

    /**
     * The availability of the doctor as a list of {@code LocalDateTime}.
     */

    private List<LocalDateTime> availability;

    /**
     * Default constructor for {@code Doctor}.
     * Initializes an empty doctor object.
     */

    public Doctor() {
    }

    /**
     * Constructs a {@code Doctor} object with the specified details.
     *
     * @param userID         the unique identifier of the doctor.
     * @param name           the name of the doctor.
     * @param password       the password for the doctor's account.
     * @param role           the role of the user (e.g., "doctor").
     * @param gender         the gender of the doctor.
     * @param age            the age of the doctor.
     * @param specialization the specialization of the doctor.
     * @param availability   the list of availability slots as {@code LocalDateTime}.
     */

    public Doctor(String userID, String name, String password, String salt, String role, String gender, int age, String specialization, List<LocalDateTime> availability) {
        super(userID, name, password, salt, role, gender, age);
        this.specialization = specialization;
        this.availability = availability;
    }

    /**
     * Gets the specialization of the doctor.
     *
     * @return the specialization of the doctor.
     */

    public String getSpecialization() {
        return specialization;
    }

    /**
     * Sets the specialization of the doctor.
     *
     * @param specialization the specialization to set.
     */

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Gets the availability of the doctor.
     *
     * @return the list of availability slots as {@code LocalDateTime}.
     */

    public List<LocalDateTime> getAvailability() {
        return availability;
    }

    /**
     * Sets the availability of the doctor.
     *
     * @param availability the list of availability slots to set.
     */

    public void setAvailability(List<LocalDateTime> availability) {
        this.availability = availability;
    }

    /**
     * Returns a string representation of the {@code Doctor} object.
     *
     * @return a string containing the doctor's details, including specialization and availability.
     */
    
    @Override
    public String toString() {
        return super.toString() + "," + specialization + "," + availability;
    }
}