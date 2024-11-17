package Controllers.ShowOptions;

/**
 * The {@code ShowDoctorMenu} class is responsible for displaying the menu options 
 * available to a doctor in the hospital management system.
 * It implements the {@code ShowUserMenu} interface.
 */

public class ShowDoctorMenu implements ShowUserMenu {

    /**
     * Displays the doctor menu with options to view and update patient records, 
     * manage personal schedule and availability, handle appointment requests, 
     * and record appointment outcomes.
     */
    
    @Override
    public void showUserMenu() {
        System.out.println("1. View Patient Medical Records");
        System.out.println("2. Update Patient Medical Records");
        System.out.println("3. View Personal Schedule");
        System.out.println("4. Set Availability for Appointments");
        System.out.println("5. Accept or Decline Appointment Requests");
        System.out.println("6. View Upcoming Appointments");
        System.out.println("7. Record Appointment Outcome");
    }
}