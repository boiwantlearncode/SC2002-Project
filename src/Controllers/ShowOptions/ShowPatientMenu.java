package Controllers.ShowOptions;

/**
 * The {@code ShowPatientMenu} class is responsible for displaying the menu options
 * available to a patient in the hospital management system.
 * It implements the {@code ShowUserMenu} interface.
 */

public class ShowPatientMenu implements ShowUserMenu {

    /**
     * Displays the patient menu with options to view and manage medical records, 
     * schedule and manage appointments, and update personal information.
     */
    
    @Override
    public void showUserMenu() {
        System.out.println("1. View Medical Record");
        System.out.println("2. Update Personal Information");
        System.out.println("3. View Available Appointment Slots");
        System.out.println("4. Schedule an Appointment");
        System.out.println("5. Reschedule an Appointment");
        System.out.println("6. Cancel an Appointment");
        System.out.println("7. View Scheduled Appointments");
        System.out.println("8. View Past Appointment Outcome Records");
        System.out.println("9. Change Password");
    }
}