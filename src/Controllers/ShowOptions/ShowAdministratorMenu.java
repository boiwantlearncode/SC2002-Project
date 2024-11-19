package Controllers.ShowOptions;

/**
 * The {@code ShowAdministratorMenu} class is responsible for displaying the menu options 
 * available to an administrator in the hospital management system.
 * It implements the {@code ShowUserMenu} interface.
 */

public class ShowAdministratorMenu implements ShowUserMenu {

    /**
     * Displays the administrator menu with options to manage hospital staff, 
     * view appointments, manage the medication inventory, and approve replenishment requests.
     */
    
    @Override
    public void showUserMenu() {
        System.out.println("1. View all Hospital Patient");
        System.out.println("2. View and Manage Hospital Staff");
        System.out.println("3. View Appointments Details");
        System.out.println("4. View and Manage Medication Inventory");
        System.out.println("5. Approve Replenishment Requests");
        System.out.println("6. Change Password");
    }
}