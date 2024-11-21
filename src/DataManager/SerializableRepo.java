package DataManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * The SerializableRepo interface defines methods for saving, loading, 
 * and retrieving data for classes that require persistent storage.
 *
 * @param <T> the type of data that this repository manages.
 */

public interface SerializableRepo<T> {

    /**
     * Saves the current state of the data to a persistent storage.
     *
     * @throws IOException if an I/O error occurs while saving the data.
     */
    
    void saveData() throws IOException;

    /**
     * Loads the state of the data from persistent storage.
     * If the storage is empty or data is corrupted, the implementation
     * should handle it appropriately.
     *
     * @throws IOException if an I/O error occurs while loading the data.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     * @throws NoSuchAlgorithmException if a required algorithm is not available 
     *         during the data validation or security checks (if applicable).
     */
    
    void loadData() throws IOException, ClassNotFoundException, NoSuchAlgorithmException;

    /**
     * Retrieves the managed data.
     *
     * @return the data of type {@code T}.
     */
    
    T getData();

}
