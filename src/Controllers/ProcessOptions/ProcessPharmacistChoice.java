package Controllers.ProcessOptions;

import Models.Pharmacist;
import Views.Pharmacist.PharmacistView;

import java.io.IOException;

/**
 * The {@code ProcessPharmacistChoice} class handles the choices made by a pharmacist in the hospital management system.
 * It provides functionality for pharmacists to manage prescriptions, inventory, and replenishment requests.
 */

public class ProcessPharmacistChoice implements ProcessUserChoice<Pharmacist>{

    /**
     * Processes the pharmacist's choice by invoking the corresponding functionality for the selected option.
     *
     * @param pharmacist the {@code Pharmacist} who is making the choice.
     * @param choice     the choice made by the pharmacist, represented as a string.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if data cannot be loaded properly.
     */
    
    @Override
    public void processUserChoice(Pharmacist pharmacist, String choice) throws IOException, ClassNotFoundException {
        PharmacistView pharmacistView = new PharmacistView();
        switch (choice) {
            case "1": pharmacistView.viewAppointmentRecordOutcome(pharmacist); break;
            case "2": pharmacistView.updatePrescriptionStatus(pharmacist); break;
            case "3": pharmacistView.viewMedicationInventory(pharmacist); break;
            case "4": pharmacistView.submitReplenishmentRequest(pharmacist); break;
            case "5": pharmacistView.changePassword(pharmacist); break;
            default: System.out.println("Invalid choice. Please try again.");
        }
    }
}
