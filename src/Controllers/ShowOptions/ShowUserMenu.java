package Controllers.ShowOptions;

/**
 * The {@code ShowUserMenu} interface defines a contract for displaying user-specific menus
 * in the hospital management system. 
 * Classes implementing this interface should provide the appropriate menu options
 * for their respective user types (e.g., Administrator, Doctor, Patient, Pharmacist).
 */

public interface ShowUserMenu {

    /**
     * Displays the menu options available for a specific user type.
     * Classes implementing this method should customize the menu based on the user's role.
     */
    
    void showUserMenu();
}