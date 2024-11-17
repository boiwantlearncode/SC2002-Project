package Models;

import java.io.Serializable;

/**
 * The {@code Medication} class represents a medication in the inventory.
 * It includes the name, stock level, and low stock alert threshold of the medication.
 * This class implements {@code Serializable} for data persistence.
 */

public class Medication implements Serializable {

    /**
     * The name of the medication.
     */

    private String name;

    /**
     * The current stock level of the medication.
     */

    private int stockLevel;

    /**
     * The threshold for low stock alerts.
     * When the stock level falls below this value, a replenishment request is needed.
     */

    private int lowStockAlert;

    /**
     * Constructs a {@code Medication} object with the specified name, stock level, and low stock alert threshold.
     *
     * @param name the name of the medication.
     * @param stockLevel the current stock level of the medication.
     * @param lowStockAlert the threshold for low stock alerts.
     */

    public Medication(String name, int stockLevel, int lowStockAlert) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowStockAlert = lowStockAlert;
    }

    /**
     * Gets the name of the medication.
     *
     * @return the name of the medication.
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the medication.
     *
     * @param name the new name of the medication.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the current stock level of the medication.
     *
     * @return the stock level of the medication.
     */

    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Sets the stock level of the medication.
     *
     * @param stockLevel the new stock level of the medication.
     */

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    /**
     * Gets the low stock alert threshold for the medication.
     *
     * @return the low stock alert threshold.
     */

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    /**
     * Sets the low stock alert threshold for the medication.
     *
     * @param lowStockAlert the new low stock alert threshold.
     */

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    /**
     * Returns a string representation of the medication.
     * The format includes the name, stock level, and low stock alert threshold.
     *
     * @return a string representation of the medication.
     */
    
    @Override
    public String toString() {
        return name + ", " + stockLevel + ", " + lowStockAlert;
    }
}