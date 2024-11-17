package Controllers.ProcessOptions;

import Models.User;

import java.io.IOException;

/**
 * The {@code ProcessUserChoice} interface defines a contract for processing user choices in a hospital management system.
 * This interface is generic and can be implemented for different types of users such as administrators, doctors, patients, and pharmacists.
 *
 * @param <T> the type of user extending the {@code User} class.
 */

public interface ProcessUserChoice<T extends User> {

    /**
     * Processes the choice made by the user by invoking the corresponding functionality for the selected option.
     *
     * @param user   the user making the choice, of type {@code T}.
     * @param choice the choice made by the user, represented as a string.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if data cannot be loaded properly.
     */
    
    public void processUserChoice(T user, String choice) throws IOException, ClassNotFoundException;
}
