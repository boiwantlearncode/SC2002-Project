package Controllers.AdministratorController;

import DataManager.InventoryRepo;
import Models.Administrator;
import Models.Inventory;
import Models.Medication;
import Views.Administrator.AdminsitratorInventoryView;

import java.io.IOException;
import java.util.List;

/**
 * The {@code AdministratorInventoryController} class is responsible for managing the hospital inventory.
 * This includes viewing, adding, updating, and deleting medications.
 */

public class AdministratorInventoryController {

    /**
     * The view layer used for displaying inventory options and receiving input from the administrator.
     */

    private AdminsitratorInventoryView inventoryView;

    /**
     * The repository responsible for loading, saving, and managing inventory data.
     */

    private InventoryRepo inventoryRepo;

    /**
     * Constructs an {@code AdministratorInventoryController} with its associated view and repository.
     */

    public AdministratorInventoryController() {
        this.inventoryView = new AdminsitratorInventoryView();
        this.inventoryRepo = new InventoryRepo();
    }

    /**
     * Manages the inventory by presenting options to the administrator.
     * The administrator can view, add, update, or delete medications.
     *
     * @param administrator the {@code Administrator} performing inventory operations.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the inventory data cannot be loaded.
     */
    
    public void manageInventory(Administrator administrator) throws IOException, ClassNotFoundException {
        while (true) {
            inventoryView.displayInventoryMenu();
            String choice = inventoryView.getChoice();

            switch (choice) {
                case "1":
                    viewInventory();
                    break;
                case "2":
                    addNewMedication();
                    break;
                case "3":
                    updateMedicationStock();
                    break;
                case "4":
                    deleteMedication();
                    break;
                case "5":
                    return;
                default:
                    inventoryView.displayError("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the current inventory to the administrator.
     *
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the inventory data cannot be loaded.
     */

    private void viewInventory() throws IOException, ClassNotFoundException {
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();
        List<Medication> medications = inventory.getMedications();

        if (medications.isEmpty()) {
            System.out.println("=====================================================");
            System.out.println("                INVENTORY MEDICATIONS                ");
            System.out.println("=====================================================");
            System.out.println("No medications found in the inventory.");
            System.out.println("=====================================================");
        } else {
            System.out.println("=====================================================");
            System.out.println("                INVENTORY MEDICATIONS                ");
            System.out.println("=====================================================");
            System.out.printf("%-20s %-10s %-25s%n", "Name", "Stock", "Low Stock Alert Level");
            System.out.println("-----------------------------------------------------");

            for (Medication medication : medications) {
                System.out.printf("%-20s %-10d %-15s%n",
                        medication.getName(),
                        medication.getStockLevel(),
                        medication.getLowStockAlert()
                );
            }

            System.out.println("=====================================================");
        }

    }

    /**
     * Adds a new medication to the inventory.
     *
     * @throws IOException              if an error occurs while saving data.
     * @throws ClassNotFoundException   if the inventory data cannot be loaded.
     * @throws IllegalArgumentException if invalid input
     */

    private void addNewMedication() throws IOException, ClassNotFoundException, IllegalArgumentException {
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();
        List<Medication> medications = inventory.getMedications();
        String name;
        int initialStock, lowStockAlertLevel;

        try {
            name = inventoryView.getMedicationName();
            initialStock = inventoryView.getInitialStock();
            lowStockAlertLevel = inventoryView.getLowStockAlertLevel();

            Medication newMedication = new Medication(name, initialStock, lowStockAlertLevel);
            medications.add(newMedication);
            inventoryView.displayMedicationAdded();

            // Save the updated inventory
            inventoryRepo.setInventory(inventory);
            inventoryRepo.saveData();
        } catch (IOException | IllegalArgumentException e) {
            inventoryView.displayError(e.getMessage());
        }

    }

    /**
     * Updates the stock level of an existing medication.
     *
     * @throws IOException              if an error occurs while saving data.
     * @throws ClassNotFoundException   if the inventory data cannot be loaded.
     * @throws IllegalArgumentException if invalid input
     */

    private void updateMedicationStock() throws IOException, ClassNotFoundException, IllegalArgumentException {
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();

        try {
            String name = inventoryView.getMedicationName();
            Medication medication = findMedicationByName(inventory, name);

            if (medication != null) {
                int newStockLevel = inventoryView.getUpdatedStockLevel();
                medication.setStockLevel(newStockLevel);
                inventoryView.displayMedicationUpdated();

                // Save the updated inventory
                inventoryRepo.setInventory(inventory);
                inventoryRepo.saveData();
            } else {
                inventoryView.displayError("Medication not found.");
            }
        } catch (IOException | IllegalArgumentException e) {
            inventoryView.displayError(e.getMessage());
        }
    }

    /**
     * Deletes a medication from the inventory.
     *
     * @throws IOException            if an error occurs while saving data.
     * @throws ClassNotFoundException if the inventory data cannot be loaded.
     */

    private void deleteMedication() throws IOException, ClassNotFoundException {
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();
        List<Medication> medications = inventory.getMedications();


        try {
            String name = inventoryView.getMedicationName();
            Medication medication = findMedicationByName(inventory, name);

            if (medication != null) {
                medications.remove(medication);
                inventoryView.displayMedicationDeleted();

                // Save the updated inventory
                inventoryRepo.setInventory(inventory);
                inventoryRepo.saveData();
            } else {
                inventoryView.displayError("Medication not found.");
            }
        } catch (IOException | ClassNotFoundException e) {
            inventoryView.displayError(e.getMessage());
        }
    }

    /**
     * Finds a medication by its name in the inventory.
     *
     * @param name the name of the medication to search for.
     * @return the {@code Medication} object if found, otherwise {@code null}.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the inventory data cannot be loaded.
     */

    private Medication findMedicationByName(Inventory inventory, String name) throws IOException, ClassNotFoundException {
        List<Medication> medications = inventory.getMedications();

        for (Medication med : medications) {
            if (med.getName().equalsIgnoreCase(name)) {
                return med;
            }
        }
        return null;
    }

    /**
     * Updates the stock level of an existing medication.
     *
     * @throws IOException            if an error occurs while saving data.
     * @throws ClassNotFoundException if the inventory data cannot be loaded.
     */
    public void replenishMedication(String name, int amount) throws IOException, ClassNotFoundException {
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();

        try {
            Medication medication = findMedicationByName(inventory, name);

            if (medication != null) {
                int newStockLevel = medication.getStockLevel() + amount;
                medication.setStockLevel(newStockLevel);
                inventoryView.displayMedicationUpdated();

                // Save the updated inventory
                inventoryRepo.setInventory(inventory);
                inventoryRepo.saveData();
            } else {
                inventoryView.displayError("Medication not found.");
            }
        } catch (IOException | ClassNotFoundException e) {
            inventoryView.displayError(e.getMessage());
        }
    }
}
