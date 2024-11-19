package Controllers.ProcessOptions;

import Controllers.AdministratorController.AdministratorController;
import Controllers.AdministratorController.AdministratorInventoryController;
import Controllers.AdministratorController.AdministratorStaffController;
import Models.Administrator;
import Views.Administrator.AdministratorView;

import java.io.IOException;

/**
 * Processes the choices made by the administrator in the hospital management system.
 * Provides functionality to manage hospital staff, view appointment details, 
 * manage inventory, and approve replenishment requests.
 * 
 * @param <Administrator> Represents the type of user (Administrator) making the choices.
 */

public class ProcessAdministratorChoice implements ProcessUserChoice<Administrator>{
    AdministratorView administratorView = new AdministratorView();
    AdministratorStaffController administratorStaffController = new AdministratorStaffController();
    AdministratorInventoryController administratorInventoryController = new AdministratorInventoryController();
    AdministratorController administratorController = new AdministratorController();
    Administrator admin = new Administrator();

    /**
     * Default constructor for the ProcessAdministratorChoice class.
     * 
     * @throws IOException            If an I/O error occurs during initialization.
     * @throws ClassNotFoundException If a required class is not found during initialization.
     */

    public ProcessAdministratorChoice() throws IOException, ClassNotFoundException {
    }

    /**
     * Constructor that initializes the ProcessAdministratorChoice with a specific administrator.
     * 
     * @param admin The administrator instance to initialize the class with.
     * @throws IOException            If an I/O error occurs during initialization.
     * @throws ClassNotFoundException If a required class is not found during initialization.
     */

    public ProcessAdministratorChoice(Administrator admin) throws IOException, ClassNotFoundException {
        admin = new Administrator();
        this.admin = admin;
    }

    /**
     * Processes the choice made by the administrator and performs the corresponding action.
     * 
     * @param administrator The administrator making the choice.
     * @param choice        The choice made by the administrator as a string.
     * @throws IOException            If an I/O error occurs while processing the choice.
     * @throws ClassNotFoundException If a required class is not found during execution.
     */

    @Override
    public void processUserChoice(Administrator administrator, String choice) throws IOException, ClassNotFoundException {
        switch (choice) {
            case "1": administratorController.viewAllPatient(admin); break;
            case "2": administratorStaffController.manageHospitalStaff(admin); break;
            case "3": administratorStaffController.viewAppointmentsDetails(admin); break;
            case "4": administratorInventoryController.manageInventory(admin); break;
            case "5": administratorController.approveReplenishmentRequests(admin); break;
            case "6": administratorView.changePassword(admin); break;
            default: System.out.println("Invalid choice. Please try again.");
        }
    }
}
