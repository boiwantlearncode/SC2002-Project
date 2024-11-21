package DataManager;

import Models.ReplenishmentRequest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The ReplenishmentRequestRepo class manages the persistence of a list of replenishment requests.
 * It provides functionality to save, load, and retrieve the list of replenishment requests using serialization.
 */

public class ReplenishmentRequestRepo implements SerializableRepo<List<ReplenishmentRequest>> {

    /**
     * Path to the file where the replenishment request data is stored.
     */
    
    private static final String REPLENISHMENT_REQUESTS_FILE = "src/DataManager/replenishment_requests.txt";

    /**
     * The list of replenishment requests managed by this repository.
     */
    
    private List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    /**
     * Saves the current list of replenishment requests to a file using serialization.
     * If the file does not exist, it will be created.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPLENISHMENT_REQUESTS_FILE))) {
            oos.writeObject(replenishmentRequests);
            //System.out.println("Replenishment requests data saved successfully.");
        }
    }

    /**
     * Loads the list of replenishment requests from a file using deserialization.
     * If the file does not exist or is empty, it initializes the list as empty.
     *
     * @throws IOException if an I/O error occurs while reading the file.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */

    @Override
    public void loadData() throws IOException, ClassNotFoundException {
        if (new File(REPLENISHMENT_REQUESTS_FILE).exists()) {
            if(Files.size(Paths.get(REPLENISHMENT_REQUESTS_FILE)) != 0){
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REPLENISHMENT_REQUESTS_FILE))) {
                    replenishmentRequests = (List<ReplenishmentRequest>) ois.readObject();
                    //System.out.println("Replenishment requests data loaded successfully.");
                }
            }
            else{
                replenishmentRequests = new ArrayList<>();
            }
        }
    }

    /**
     * Retrieves the current list of replenishment requests.
     *
     * @return the list of replenishment requests.
     */

    @Override
    public List<ReplenishmentRequest> getData() {
        return replenishmentRequests;
    }

    /**
     * Sets the list of replenishment requests to the specified value.
     *
     * @param replenishmentRequests the list of replenishment requests to be set.
     */

    public void setReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        this.replenishmentRequests = replenishmentRequests;
    }
}
