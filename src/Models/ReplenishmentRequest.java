package Models;

import DataManager.ReplenishmentRequestRepo;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * The {@code ReplenishmentRequest} class represents a request to replenish the stock of a specific medication.
 * It contains details about the medication, the requested quantity, approval status, and a unique identifier for the request.
 * This class implements {@code Serializable} to support data persistence.
 */

public class ReplenishmentRequest implements Serializable {

    /**
     * The medication for which the replenishment is requested.
     */

    private Medication medication;

    /**
     * The unique identifier for this replenishment request.
     */

    private String id;

    /**
     * The quantity of medication requested.
     */

    private int quantity;

    /**
     * The approval status of the replenishment request.
     * {@code true} if the request is approved, otherwise {@code false}.
     */

    private boolean approved;

    /**
     * Constructs an empty {@code ReplenishmentRequest} object.
     */

    public ReplenishmentRequest() {
    }

    /**
     * Constructs a {@code ReplenishmentRequest} object with the specified medication, quantity, and approval status.
     *
     * @param medication the {@code Medication} associated with the request.
     * @param quantity   the quantity of medication requested.
     * @param approved   the approval status of the request.
     */

    public ReplenishmentRequest(Medication medication, int quantity, boolean approved) {
        ReplenishmentRequestRepo replenishmentRequests = new ReplenishmentRequestRepo();
        List<ReplenishmentRequest> requests;
        try {
            replenishmentRequests.loadData();
            requests = replenishmentRequests.getData();

            this.id = "REQ" + (requests.size() + 1);
            this.medication = medication;
            this.quantity = quantity;
            this.approved = approved;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error creating replenishment request.");
        }

    }

    /**
     * Retrieves the medication associated with this replenishment request.
     *
     * @return the {@code Medication} object.
     */

    public Medication getMedication() {
        return medication;
    }

    /**
     * Sets the medication for this replenishment request.
     *
     * @param medication the {@code Medication} object to be set.
     */

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    /**
     * Retrieves the quantity of medication requested.
     *
     * @return the requested quantity as an {@code int}.
     */

    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of medication requested.
     *
     * @param quantity the requested quantity as an {@code int}.
     */

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Checks whether this replenishment request is approved.
     *
     * @return {@code true} if the request is approved, otherwise {@code false}.
     */

    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the approval status of this replenishment request.
     *
     * @param approved the approval status to set ({@code true} for approved, {@code false} otherwise).
     */

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * Retrieves the unique identifier for this replenishment request.
     *
     * @return the identifier as a {@code String}.
     */

    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this replenishment request.
     *
     * @param id the identifier as a {@code String}.
     */
    
    public void setId(String id) {
        this.id = id;
    }
}