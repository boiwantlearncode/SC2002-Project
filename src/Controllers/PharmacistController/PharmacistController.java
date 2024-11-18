package Controllers.PharmacistController;

import DataManager.AppointmentsRepo;
import DataManager.InventoryRepo;
import DataManager.ReplenishmentRequestRepo;
import Models.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code PharmacistController} class is responsible for handling pharmacist-specific operations.
 * These include updating prescription statuses and submitting replenishment requests for medications.
 */

public class PharmacistController {

    /**
     * A scanner for user input.
     */

    Scanner scanner = new Scanner(System.in);

    /**
     * Updates the prescription status of a given appointment to "dispensed".
     *
     * @param appointmentID the ID of the appointment whose prescription status needs to be updated.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the appointment data cannot be loaded.
     */

    public void updatePrescriptionStatus(String appointmentID) throws IOException, ClassNotFoundException {
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        Appointment existingAppointment = null;
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                existingAppointment = appointment;
                break;
            }
        }

        if (existingAppointment == null) {
            System.out.println("Appointment not found!!");
        } else {
            AppointmentOutcomeRecord appointmentOutcomeRecord = existingAppointment.getOutcomeRecord();
            if (appointmentOutcomeRecord == null) {
                System.out.println("Appointment Outcome Record not found!!");
                return;
            } else {
                List<Prescription> prescriptions = appointmentOutcomeRecord.getPrescriptions();

                InventoryRepo inventoryRepo = new InventoryRepo();
                inventoryRepo.loadData();
                Inventory inventory = inventoryRepo.getData();
                List<Medication> medications = inventory.getMedications();

                for (Prescription prescription : prescriptions) {
                    if (prescription.getStatus().equals("Pending")) {
                        Medication prescribedMedication = prescription.getMedication();
    
                        Medication inventoryMedication = null;
                        for (Medication medication : medications) {
                            if (medication.getName().equals(prescribedMedication.getName())) {
                                inventoryMedication = medication;
                                break;
                            }
                        }
    
                        if (inventoryMedication == null) {
                            System.out.println("Medication " + prescribedMedication.getName() + " not found in inventory!");
                            continue;
                        }

                        int currentStock = inventoryMedication.getStockLevel();
                        if (currentStock > 0) {
                            inventoryMedication.setStockLevel(currentStock - 1);
                            prescription.setStatus("Dispensed");
                            System.out.println("Medication " + prescribedMedication.getName() + " dispensed. Remaining stock: " + inventoryMedication.getStockLevel());
                        } else {
                            System.out.println("Medication " + prescribedMedication.getName() + " is out of stock!");
                        }
                    }
                }
                inventoryRepo.saveData();
                
                appointmentsRepo.setAppointments(appointments);
                appointmentsRepo.saveData();
            }
        }
    }

    /**
     * Submits a replenishment request for a specific medication.
     *
     * @param medicationName the name of the medication for which the replenishment request is being submitted.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if the inventory data cannot be loaded.
     * @throws InputMismatchException if quantity input is not in integer format
     */
    
    public void submitReplenishmentRequest(String medicationName) throws IOException, ClassNotFoundException, InputMismatchException {
        InventoryRepo inventoryRepo = new InventoryRepo();
        inventoryRepo.loadData();
        Inventory inventory = inventoryRepo.getData();
        List<Medication> medications = inventory.getMedications();

        Medication existingMedication = null;
        boolean foundMedication = false;
        for (Medication medication : medications) {
            if (medication.getName().equals(medicationName)) {
                existingMedication = medication;
                foundMedication = true;
                break;
            }
        }

        if (!foundMedication) {
            System.out.println("Medication not found!!\nNo changes made");
        } else {
            System.out.println("Enter quantity: ");
            try {
                int quantity = scanner.nextInt();
                if (quantity < 0) {
                    throw new IOException("Quantity cannot be negative.");
                }
                ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(existingMedication, quantity, false);
                ReplenishmentRequestRepo replenishmentRequestRepo = new ReplenishmentRequestRepo();
                replenishmentRequestRepo.loadData();

                List<ReplenishmentRequest> replenishmentRequests = replenishmentRequestRepo.getData();
                replenishmentRequests.add(replenishmentRequest);
                replenishmentRequestRepo.setReplenishmentRequests(replenishmentRequests);
                replenishmentRequestRepo.saveData();
            } catch (InputMismatchException e) {
                throw new InputMismatchException("Quantity must be a positive integer.");
            }

        }
    }
}