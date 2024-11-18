package Views.Administrator;

import Models.Medication;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code AdminsitratorInventoryView} class provides the view layer for managing
 * the medication inventory in a hospital management system. It handles the display of
 * inventory options, inputs from administrators, and feedback messages.
 */

public class AdminsitratorInventoryView {

    /**
     * A {@code Scanner} object to handle input from the administrator.
     */

    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the menu for managing the medication inventory.
     * Provides options to view, add, update, or delete medications.
     */

    public void displayInventoryMenu() {
        System.out.println("\n--- Manage Medication Inventory ---");
        System.out.println("1. View inventory");
        System.out.println("2. Add new medication");
        System.out.println("3. Update stock");
        System.out.println("4. Delete medication");
        System.out.println("5. Return to main menu");
        System.out.print("Enter your choice: ");
    }

    /**
     * Gets the administrator's choice for inventory management.
     *
     * @return the administrator's choice as a {@code String}.
     */

    public String getChoice() {
        return scanner.nextLine();
    }

    /**
     * Prompts the administrator to enter the name of a medication.
     *
     * @return the name of the medication as a {@code String}.
     */

    public String getMedicationName() throws IOException {
        System.out.print("Enter medication name: ");
        String name = scanner.nextLine();

        if (name == null || name.isEmpty()) {
            throw new IOException("Medication name cannot be empty");
        }
        return name;
    }

    /**
     * Prompts the administrator to enter the initial stock level for a new medication.
     *
     * @return the initial stock level as an {@code int}.
     */

    public int getInitialStock() throws IOException, IllegalArgumentException {
        System.out.print("Enter initial stock: ");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Stock value cannot be empty.");
        }

        int initialStock = Integer.parseInt(input);
        if (initialStock < 0) {
            throw new IllegalArgumentException("Stock value cannot be negative.");
        }

        return initialStock;
    }

    /**
     * Prompts the administrator to enter the low stock alert level for a new medication.
     *
     * @return the low stock alert level as an {@code int}.
     */

    public int getLowStockAlertLevel() throws IOException, IllegalArgumentException {
        System.out.print("Enter low stock alert level: ");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Stock value cannot be empty.");
        }

        int lowStockAlertLevel = Integer.parseInt(input);
        if (lowStockAlertLevel < 0) {
            throw new IllegalArgumentException("Stock value cannot be negative.");
        }

        return lowStockAlertLevel;
    }

    /**
     * Prompts the administrator to enter the updated stock level for an existing medication.
     *
     * @return the updated stock level as an {@code int}.
     */

    public int getUpdatedStockLevel() throws IOException, IllegalArgumentException {
        System.out.print("Enter new stock level: ");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Stock value cannot be empty.");
        }

        int newStockLevel = Integer.parseInt(input);
        if (newStockLevel < 0) {
            throw new IllegalArgumentException("Stock value cannot be negative.");
        }

        return newStockLevel;
    }

    /**
     * Displays the details of a specific medication.
     *
     * @param medication the {@code Medication} object whose details will be displayed.
     */

    public void displayInventory(Medication medication) {
        System.out.println("Medication Name: " + medication.getName());
        System.out.println("Stock: " + medication.getStockLevel());
        System.out.println("Low Stock Alert Level: " + medication.getLowStockAlert());
    }

    /**
     * Displays a message indicating that a new medication has been added successfully.
     */

    public void displayMedicationAdded() {
        System.out.println("New medication added successfully.");
    }

    /**
     * Displays a message indicating that a medication's stock has been updated successfully.
     */

    public void displayMedicationUpdated() {
        System.out.println("Medication stock updated successfully.");
    }

    /**
     * Displays a message indicating that a medication has been deleted successfully.
     */

    public void displayMedicationDeleted() {
        System.out.println("Medication deleted successfully.");
    }

    /**
     * Displays an error message for invalid inputs or actions.
     *
     * @param message the error message to be displayed as a {@code String}.
     */
    
    public void displayError(String message) {
        System.out.println("Error: " + message);
    }
}