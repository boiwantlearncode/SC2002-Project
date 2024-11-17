package Views.Administrator;

import Models.ReplenishmentRequest;

import java.util.List;
import java.util.Scanner;

/**
 * The {@code AdministratorView} class provides the view layer for handling
 * replenishment requests. It is responsible for displaying requests, receiving
 * user input for approval decisions, and providing feedback messages.
 */

public class AdministratorView {

    /**
     * A {@code Scanner} object to handle administrator input.
     */

    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays all replenishment requests.
     *
     * @param requests a list of {@code ReplenishmentRequest} objects to display.
     *                 If the list is empty, a message indicating no requests is displayed.
     */

    public void displayReplenishmentRequests(List<ReplenishmentRequest> requests) {
        System.out.println("\n--- Replenishment Requests ---");
        if (requests.isEmpty()) {
            System.out.println("No replenishment requests to display.");
        } else {
            for (ReplenishmentRequest request : requests) {
                System.out.println("Request ID: " + request.getId());
                System.out.println("Medication: " + request.getMedication());
                System.out.println("Requested Quantity: " + request.getQuantity());
                System.out.println("Status: " + (request.isApproved() ? "Approved" : "Pending"));
                System.out.println("---------------");
            }
        }
    }

    /**
     * Prompts the administrator to approve or reject a specific request.
     *
     * @param requestId the ID of the request to approve or reject.
     * @return the administrator's decision as a {@code String}, which will be "yes" or "no".
     */

    public String getApprovalDecision(String requestId) {
        System.out.print("Approve request ID " + requestId + "? (yes/no): ");
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Displays a success message after a request has been approved.
     *
     * @param requestId the ID of the approved request.
     */

    public void displayApprovalSuccess(String requestId) {
        System.out.println("Request ID " + requestId + " approved successfully.");
    }

    /**
     * Displays an error message if approval fails.
     *
     * @param requestId the ID of the request that failed to be approved.
     */

    public void displayApprovalFailure(String requestId) {
        System.out.println("Failed to approve request ID " + requestId + ". Please try again.");
    }

    /**
     * Displays a message for invalid or already approved requests.
     *
     * @param requestId the ID of the invalid or already approved request.
     */
    
    public void displayInvalidRequestMessage(String requestId) {
        System.out.println("Request ID " + requestId + " not found or already approved.");
    }
}