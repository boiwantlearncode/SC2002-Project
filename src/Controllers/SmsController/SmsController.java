package Controllers.SmsController;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Base64;

import Models.Doctor;
import Models.Patient;

/**
 * The SmsController class provides functionality to send SMS notifications to patients
 * regarding their appointment schedule, rescheduling, or cancellations using the Twilio API.
 * <p>
 * Note: This implementation uses the Twilio trial version, so the recipient's phone number
 * must be verified in Twilio's console for the SMS to be successfully sent.
 */

public class SmsController {

    /**
     * Sends an SMS to a patient regarding their appointment details.
     *
     * @param appointmentType     The type of appointment operation ("schedule", "reschedule", or "cancel").
     * @param appointmentDateTime The date and time of the appointment.
     * @param doctor              The doctor associated with the appointment.
     * @param patient             The patient receiving the appointment notification.
     */

    public static void SendSMS(String appointmentType, LocalDateTime appointmentDateTime, Doctor doctor, Patient patient) {
        try {
            String accountSID = "ACa972b1703995356089d4f2dc1ae4503b";
            String authToken = "42fed2832eb7f35682eaf1dda896101b";
            String fromPhone = "+14242968356";
            String toPhone = "+65"+ patient.getPhoneNumber();
            String message = "[Hospital Management System]";

            if (appointmentType == "schedule") {
                message = "[Hospital Management System] Dear " + patient.getName() + ", you have an upcoming appointment scheduled on " + appointmentDateTime + " with doctor " + doctor.getName();
            } else if (appointmentType == "reschedule") {
                message = "[Hospital Management System] Dear " + patient.getName() + ", you have rescheduled your appointment to " + appointmentDateTime + " with doctor " + doctor.getName();
            } else if (appointmentType == "cancel") {
                message = "[Hospital Management System] Dear " + patient.getName() + ", you have cancelled your scheduled appointment on " + appointmentDateTime + " with doctor " + doctor.getName();
            }

            String url = "https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json";
            String data = "From=" + URLEncoder.encode(fromPhone, "UTF-8") + "&To=" + URLEncoder.encode(toPhone, "UTF-8") + "&Body=" + URLEncoder.encode(message, "UTF-8");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            String authStr = accountSID + ":" + authToken;
            String encodedAuth = Base64.getEncoder().encodeToString(authStr.getBytes());
            con.setRequestProperty("Authorization", "Basic " + encodedAuth);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream os = con.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

            int responseCode = con.getResponseCode();
            //System.out.println("Response Code: " + responseCode);
            System.out.println("A SMS has been sent to your phone number: " + patient.getPhoneNumber());
        } catch (Exception e) {
            System.out.println("Error sending SMS - You are using Trial version (Requires user's phone number to be verified.)");
            e.printStackTrace();
        }
    }
}
