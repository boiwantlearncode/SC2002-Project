package DataManager;

import Models.ReplenishmentRequest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReplenishmentRequestRepo implements SerializableRepo<List<ReplenishmentRequest>> {
    private static final String REPLENISHMENT_REQUESTS_FILE = "src/DataManager/replenishment_requests.txt";
    private List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REPLENISHMENT_REQUESTS_FILE))) {
            oos.writeObject(replenishmentRequests);
            //System.out.println("Replenishment requests data saved successfully.");
        }
    }

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

    @Override
    public List<ReplenishmentRequest> getData() {
        return replenishmentRequests;
    }

    public void setReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        this.replenishmentRequests = replenishmentRequests;
    }
}
