package Controllers.AdministratorController;

import Controllers.AuthenticationController.PasswordController.PasswordController;
import DataManager.ReplenishmentRequestRepo;
import Models.Administrator;
import Models.Patient;
import Models.ReplenishmentRequest;
import Views.Administrator.AdministratorView;

import java.io.IOException;
import java.util.List;

/**
 * The {@code AdministratorController} class handles replenishment request management for administrators.
 * It interacts with the view layer to display requests and gathers decisions from the administrator.
 */

public class AdministratorController {
    private AdministratorView requestView;
    private List<ReplenishmentRequest> requests;
    private ReplenishmentRequestRepo requestRepo;
    AdministratorInventoryController administratorInventoryController = new AdministratorInventoryController();

    /**
     * Initializes the {@code AdministratorController}.
     *
     * @throws IOException            if an error occurs while accessing data.
     * @throws ClassNotFoundException if the serialized data cannot be found.
     */

    public AdministratorController() throws IOException, ClassNotFoundException {
        this.requestView = new AdministratorView();
        this.requestRepo = new ReplenishmentRequestRepo();
        loadRequests();
    }

    /**
     * Displays all patient in the hospital.
     * @param admin 
     *
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the user data cannot be loaded.
     */

    public void viewAllPatient(Administrator admin) throws IOException, ClassNotFoundException {
        System.out.println("All Patient:");
        requestView.displayAllPatient();
    }

    /**
     * Loads replenishment requests from the repository.
     *
     * @throws IOException            if an error occurs during data loading.
     * @throws ClassNotFoundException if the serialized data cannot be found.
     */

    private void loadRequests() throws IOException, ClassNotFoundException {
        requestRepo.loadData();
        this.requests = requestRepo.getData();
    }

    /**
     * Manages the approval of replenishment requests by the administrator.
     * Displays all requests and prompts the administrator to approve or skip unapproved requests.
     *
     * @param admin the {@code Administrator} making the approvals.
     * @throws IOException            if an error occurs while saving approvals.
     * @throws ClassNotFoundException if an error occurs when calling replenishMedication()
     */

    public void approveReplenishmentRequests(Administrator admin) throws IOException, ClassNotFoundException {
        loadRequests();
        requestView.displayReplenishmentRequests(requests);

        for (ReplenishmentRequest request : requests) {
            try {
                if (!request.isApproved()) {  // Only prompt for unapproved requests
                    String decision = requestView.getApprovalDecision(request.getId());

                    if (decision.equals("yes")) {
                        administratorInventoryController.replenishMedication(request.getMedication().getName(), request.getQuantity());
                        request.setApproved(true);
                        requestView.displayApprovalSuccess(request.getId());
                        saveRequests();  // Save each time a request is approved
                    } else if (decision.equals("no")) {
                        System.out.println("Skipping approval for request ID " + request.getId());
                    } else {
                        requestView.displayApprovalFailure(request.getId());
                    }
                } else {
                    requestView.displayInvalidRequestMessage(request.getId());
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Saves the updated list of replenishment requests to the repository.
     * If saving fails, an error message is displayed.
     */

    private void saveRequests() {
        try {
            requestRepo.setReplenishmentRequests(requests);
            requestRepo.saveData();
        } catch (IOException e) {
            System.out.println("Failed to save replenishment request approvals.");
        }
    }

}
