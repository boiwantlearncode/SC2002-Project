package Models;

import java.io.Serializable;

/**
 * The {@code Prescription} class represents a prescription issued to a patient.
 * It contains details about the prescribed medication and the status of the prescription.
 * This class implements {@code Serializable} to allow data persistence.
 */

public class Prescription implements Serializable {

    /**
     * The medication prescribed.
     */

    private Medication medication;

    /**
     * The status of the prescription (e.g., "pending", "dispensed").
     */

    private String Status;

    /**
     * Constructs an empty {@code Prescription} object.
     */

    public Prescription() {
    }

    /**
     * Constructs a {@code Prescription} object with the specified medication and status.
     *
     * @param medication the {@code Medication} associated with the prescription.
     * @param status     the status of the prescription (e.g., "pending", "dispensed").
     */

    public Prescription(Medication medication, String status) {
        this.medication = medication;
        this.Status = status;
    }

    /**
     * Retrieves the medication associated with this prescription.
     *
     * @return the {@code Medication} object.
     */

    public Medication getMedication() {
        return medication;
    }

    /**
     * Sets the medication for this prescription.
     *
     * @param medication the {@code Medication} object to be set.
     */

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    /**
     * Retrieves the current status of this prescription.
     *
     * @return the status of the prescription as a {@code String}.
     */

    public String getStatus() {
        return Status;
    }

    /**
     * Sets the status of this prescription.
     *
     * @param status the status to be set (e.g., "pending", "dispensed").
     */
    
    public void setStatus(String status) {
        this.Status = status;
    }
}