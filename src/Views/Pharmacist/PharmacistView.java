package Views.Pharmacist;

import Controllers.AuthenticationController.PasswordController.PasswordController;
import Controllers.PharmacistController.PharmacistController;
import DataManager.AppointmentsRepo;
import DataManager.InventoryRepo;
import Models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    public void viewAppointmentRecordOutcome(Pharmacist pharmacist) throws IOException, ClassNotFoundException {
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        appointments.removeIf(apt -> apt.getOutcomeRecord() == null || !apt.getStatus().equals("Completed"));

        boolean hasPending = false;      
        System.out.println("=== Appointments with Pending Prescriptions ===");

        for (Appointment apt : appointments) {
            AppointmentOutcomeRecord record = apt.getOutcomeRecord();

            List<Prescription> pendingPrescriptions = new ArrayList<>();
            for (Prescription prescription : record.getPrescriptions()) {
                if (prescription.getStatus().equals("Pending")) {
                    pendingPrescriptions.add(prescription);
                    hasPending = true;
                }
            }

            if (!pendingPrescriptions.isEmpty()) {
                System.out.println("Patient ID: " + apt.getPatientID() +
                        " | Appointment ID: " + apt.getAppointmentID() +
                        " | Services Provided: " + record.getServiceType() +
                        " | Consultation Notes: " + record.getConsultationNotes());
                System.out.println("Prescriptions:");
                for (Prescription prescription : pendingPrescriptions) {
                    Medication medication = prescription.getMedication();
                    System.out.println(" - Medication: " + medication.getName() +
                            " | Status: " + prescription.getStatus());
                }
            }
        }

        if (!hasPending) {
            System.out.println("No appointments with pending prescription.");
        }
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

    public void submitReplenishmentRequest(Pharmacist pharmacist) throws IOException, ClassNotFoundException, InputMismatchException {
        try {
            PharmacistController pharmacistController = new PharmacistController();
            viewMedicationInventory(pharmacist);

            System.out.println("Enter the medication:");
            String medicationName = scanner.nextLine();
            if (medicationName.isEmpty()) {
                throw new IOException("Medication cannot be empty.");
            }
            pharmacistController.submitReplenishmentRequest(medicationName);
        } catch (IOException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
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

        if (medications.isEmpty()) {
            System.out.println("==============================================================");
            System.out.println("                     MEDICATIONS INVENTORY                    ");
            System.out.println("==============================================================");
            System.out.println("No medications found in the inventory.");
            System.out.println("==============================================================");
        } else {
            System.out.println("==============================================================");
            System.out.println("                     MEDICATIONS INVENTORY                    ");
            System.out.println("==============================================================");
            System.out.printf("%-20s | %-15s | %-20s%n", "Name", "Stock Level", "Low Stock Alert");
            System.out.println("--------------------------------------------------------------");

            for (Medication medication : medications) {
                String lowStockAlert = (medication.getStockLevel() <= medication.getLowStockAlert()) ? "Yes" : "No";

                System.out.printf("%-20s | %-15d | %-20s%n",
                        medication.getName(),
                        medication.getStockLevel(),
                        lowStockAlert
                );
            }

            System.out.println("==============================================================");
        }
    }

    /**
     * Allows the patient to change their password.
     *
     * @param pharmacist the {@code Pharmacist} whose password is to be changed.
     * @throws IOException            if an error occurs while saving the updated password.
     * @throws ClassNotFoundException if the data file cannot be found or loaded.
     */

    public void changePassword(Pharmacist pharmacist) throws IOException, ClassNotFoundException {
        try {
            PasswordController passwordController = new PasswordController();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            passwordController.changePassword(newPassword, pharmacist.getUserID());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Invalid password format, please try again.");
        }
    }
}