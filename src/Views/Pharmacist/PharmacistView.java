package Views.Pharmacist;

import Controllers.PharmacistController.PharmacistController;
import DataManager.InventoryRepo;
import Models.Inventory;
import Models.Medication;
import Models.Pharmacist;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code PharmacistView} class provides methods for pharmacists to interact with the application.
 * This includes viewing the medication inventory, updating prescription status, and submitting replenishment requests.
 */

public class PharmacistView {

    /**
     * Scanner object for handling user input.
     */

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Displays the outcome record of appointments handled by the pharmacist.
     * (Currently not implemented.)
     *
     * @param pharmacist the {@code Pharmacist} whose appointment records are to be viewed.
     */

    public void viewAppointmentRecordOutcome(Pharmacist pharmacist) {
        // Not implemented yet
    }

    /**
     * Allows the pharmacist to update the prescription status for a specific appointment.
     *
     * @param pharmacist the {@code Pharmacist} performing the operation.
     * @throws IOException            if an error occurs while accessing the data.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */

    public void updatePrescriptionStatus(Pharmacist pharmacist) throws IOException, ClassNotFoundException {
        PharmacistController pharmacistController = new PharmacistController();
        System.out.println("Enter appointment ID: ");
        String appointmentID = scanner.nextLine();

        pharmacistController.updatePrescriptionStatus(appointmentID);
    }

    /**
     * Allows the pharmacist to submit a replenishment request for a specific medication.
     *
     * @param pharmacist the {@code Pharmacist} submitting the request.
     * @throws IOException            if an error occurs while accessing the data.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */

    public void submitReplenishmentRequest(Pharmacist pharmacist) throws IOException, ClassNotFoundException {
        PharmacistController pharmacistController = new PharmacistController();
        viewMedicationInventory(pharmacist);

        System.out.println("Enter the medication:");
        String medicationName = scanner.nextLine();
        pharmacistController.submitReplenishmentRequest(medicationName);
    }

    /**
     * Displays the current medication inventory, including the name and stock level of each medication.
     *
     * @param pharmacist the {@code Pharmacist} viewing the inventory.
     * @throws IOException            if an error occurs while accessing the data.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */
    
    public void viewMedicationInventory(Pharmacist pharmacist) throws IOException, ClassNotFoundException {
        InventoryRepo inventoryRepo = new InventoryRepo();
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();

        List<Medication> medications = inventory.getMedications();

        System.out.println("MEDICATIONS INVENTORY");
        for (Medication medication : medications) {
            System.out.println("Name: " + medication.getName() + "\nStock Level: " + medication.getStockLevel());
        }
    }
}