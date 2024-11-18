package Views.Doctor;

import Controllers.DoctorController.DoctorController;
import DataManager.AppointmentsRepo;
import Models.Appointment;
import Models.Doctor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code DoctorAppointmentView} class provides the user interface for doctors to manage their appointments.
 * This includes accepting or declining appointments, viewing upcoming appointments, and recording appointment outcomes.
 */

public class DoctorAppointmentView {

    /**
     * A {@code Scanner} object to handle input from the doctor.
     */

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Allows the doctor to accept or decline an appointment based on its ID.
     *
     * @param doctor the {@code Doctor} managing the appointments.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the appointments data cannot be loaded.
     */

    public void acceptOrDeclineAppointment(Doctor doctor) throws IOException, ClassNotFoundException {
        DoctorController doctorController = new DoctorController();
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();

        // Filter appointments for the given doctor
        appointments.removeIf(appointment -> !appointment.getDoctorID().equals(doctor.getUserID()));
        // Filter appointments to get only those of "Pending" status
        appointments.removeIf(appointment -> !appointment.getStatus().equals("Pending"));

        if (appointments.isEmpty()) {
            System.out.println("No appointment requests found!!");
            return;
        }

        System.out.println("APPOINTMENTS");
        for (Appointment apt : appointments) {
            System.out.println("ID: " + apt.getAppointmentID() + ", Patient: " + apt.getPatientID() +
                    ", Date/Time: " + apt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ", Status: " + apt.getStatus());
        }

        System.out.println("Enter appointment ID: ");
        String appointmentID = scanner.nextLine();

        doctorController.acceptOrDeclineAppointment(doctor, appointmentID);
    }

    /**
     * Displays all upcoming appointments for the doctor.
     *
     * @param doctor the {@code Doctor} whose appointments are to be viewed.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the appointments data cannot be loaded.
     */

    public void viewUpcomingAppointments(Doctor doctor) throws IOException, ClassNotFoundException {
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();
        LocalDateTime currentTime = LocalDateTime.now();

        // Filter for upcoming appointments belonging to the doctor
        appointments.removeIf(apt -> !(apt.getAppointmentTime().isAfter(currentTime)) || !apt.getDoctorID().equals(doctor.getUserID()));

        if (appointments.isEmpty()) {
            System.out.println("No appointments found!!");
            return;
        }

        System.out.println("Upcoming appointment(s):");
        for (Appointment apt : appointments) {
            System.out.println("ID: " + apt.getAppointmentID() + ", Patient: " + apt.getPatientID() +
                    ", Date/Time: " + apt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ", Status: " + apt.getStatus());
        }
    }

    /**
     * Allows the doctor to record the outcome of a past appointment.
     *
     * @param doctor the {@code Doctor} managing the appointment outcomes.
     * @throws IOException            if an error occurs during data access.
     * @throws ClassNotFoundException if the appointments data cannot be loaded.
     */
    
    public void recordAppointmentOutcome(Doctor doctor) throws IOException, ClassNotFoundException {
        DoctorController doctorController = new DoctorController();
        AppointmentsRepo appointmentsRepo = new AppointmentsRepo();
        appointmentsRepo.loadData();
        List<Appointment> appointments = appointmentsRepo.getData();
        LocalDateTime currentTime = LocalDateTime.now();

        // Filter for past appointments belonging to the doctor
        appointments.removeIf(apt -> !apt.getAppointmentTime().isBefore(currentTime) || !apt.getDoctorID().equals(doctor.getUserID()));

        if (appointments.isEmpty()) {
            System.out.println("No appointments found!!");
            return;
        }

        System.out.println("PAST APPOINTMENTS");
        for (Appointment apt : appointments) {
            System.out.println("ID: " + apt.getAppointmentID() + ", Patient: " + apt.getPatientID() +
                    ", Date/Time: " + apt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }

        System.out.println("Enter appointment ID: ");
        String appointmentID = scanner.nextLine();

        doctorController.recordAppointmentOutcome(doctor, appointmentID);
    }
}