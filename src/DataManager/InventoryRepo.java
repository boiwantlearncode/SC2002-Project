package DataManager;

import Models.Inventory;
import Models.Medication;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepo implements SerializableRepo<Inventory> {
    private static final String INVENTORY_FILE = "src/DataManager/inventory.txt";
    private Inventory inventory = new Inventory();

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INVENTORY_FILE))) {
            oos.writeObject(inventory);
            System.out.println("Inventory data saved successfully.");
        }
    }

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
                    System.out.println("Inventory data loaded successfully.");
                }
            }
        }
    }

    @Override
    public Inventory getData() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
