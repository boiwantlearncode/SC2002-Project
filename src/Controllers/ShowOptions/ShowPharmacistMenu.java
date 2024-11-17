package Controllers.ShowOptions;

/**
 * The {@code ShowPharmacistMenu} class is responsible for displaying the menu options
 * available to a pharmacist in the hospital management system.
 * It implements the {@code ShowUserMenu} interface.
 */

public class ShowPharmacistMenu implements ShowUserMenu {

    /**
     * Displays the pharmacist menu with options to view appointment outcome records, 
     * update prescription status, view the medication inventory, and submit replenishment requests.
     */
    
    @Override
    public void showUserMenu() {
        System.out.println("1. View Appointment Outcome Record");
        System.out.println("2. Update Prescription Status");
        System.out.println("3. View Medication Inventory");
        System.out.println("4. Submit Replenishment Request");
    }
}