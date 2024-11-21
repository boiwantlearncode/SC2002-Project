package DataManager;

import Models.Inventory;
import Models.Medication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The InventoryRepo class manages the persistence of the Inventory object. 
 * It provides functionality to save, load, and retrieve inventory data using serialization.
 * If the inventory file is empty, it initializes the inventory with sample data.
 */

public class InventoryRepo implements SerializableRepo<Inventory> {

    /**
     * Path to the file where the inventory data is stored.
     */
    
    private static final String INVENTORY_FILE = "src/DataManager/inventory.txt";

    /**
     * The Inventory object managed by this repository.
     */
    
    private Inventory inventory = new Inventory();

    /**
     * Saves the current inventory data to a file using serialization.
     * If the file does not exist, it will be created.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);
            //System.out.println("Inventory data saved successfully.");
        }
    }

    /**
     * Loads the inventory data from a file using deserialization.
     * If the file does not exist or is empty, it initializes the inventory with sample data.
     *
     * @throws IOException if an I/O error occurs while reading the file.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */

    @Override
    public void loadData() throws IOException, ClassNotFoundException {
        if (new File(INVENTORY_FILE).exists()) {
            if (Files.size(Paths.get(INVENTORY_FILE)) == 0) {
                List<Medication> medications = new ArrayList<>();
                inventory.setMedications(medications);
                // File is empty, populate with initial data
                inventory.getMedications().add(new Medication("Paracetamol", 100, 20));
                inventory.getMedications().add(new Medication("Ibuprofen", 80, 15));
                inventory.getMedications().add(new Medication("Amoxicillin", 50, 10));
                setInventory(inventory);
                saveData();
                System.out.println("Initialized inventory.txt with sample data.");
            } else {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(INVENTORY_FILE))) {
                    inventory = (Inventory) ois.readObject();
                    //System.out.println("Inventory data loaded successfully.");
                }
            }
        }
    }

    /**
     * Retrieves the current inventory data.
     *
     * @return the Inventory object containing the current inventory data.
     */

    @Override
    public Inventory getData() {
        return inventory;
    }

    /**
     * Sets the inventory object to the specified value.
     *
     * @param inventory the Inventory object to be set.
     */

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
