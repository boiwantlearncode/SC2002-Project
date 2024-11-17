package Models;

import java.io.Serializable;
import java.util.List;

/**
 * The {@code Inventory} class represents the inventory of medications in the hospital management system.
 * It includes a list of medications and provides methods to access and modify the inventory.
 * This class implements {@code Serializable} for data persistence.
 */

public class Inventory implements Serializable {

    /**
     * A list of {@code Medication} objects representing the medications in the inventory.
     */

    private List<Medication> medications;

    /**
     * Default constructor for {@code Inventory}.
     * Initializes an empty inventory.
     */

    public Inventory() {}

    /**
     * Constructs an {@code Inventory} object with the specified list of medications.
     *
     * @param medications a list of {@code Medication} objects to initialize the inventory.
     */

    public Inventory(List<Medication> medications) {
        this.medications = medications;
    }

    /**
     * Gets the list of medications in the inventory.
     *
     * @return a list of {@code Medication} objects.
     */

    public List<Medication> getMedications() {
        return medications;
    }

    /**
     * Sets the list of medications in the inventory.
     *
     * @param medications a list of {@code Medication} objects to set in the inventory.
     */
    
    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }
}