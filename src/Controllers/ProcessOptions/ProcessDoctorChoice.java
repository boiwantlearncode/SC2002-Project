package Controllers.ProcessOptions;

import Controllers.DoctorController.DoctorController;
import Models.Doctor;
import Views.Doctor.DoctorAppointmentView;
import Views.Doctor.DoctorPatientView;
import Views.Doctor.DoctorView;

import java.io.IOException;

/**
 * The {@code ProcessDoctorChoice} class handles the choices made by a doctor in the hospital management system.
 * It delegates functionality to various views and controllers based on the doctor's selected option.
 */

public class ProcessDoctorChoice implements ProcessUserChoice<Doctor>{

    /**
     * Processes the user's choice by invoking the corresponding functionality for the doctor.
     *
     * @param doctor the {@code Doctor} who is making the choice.
     * @param choice the choice made by the doctor, represented as a string.
     * @throws IOException            if an error occurs during data access or saving.
     * @throws ClassNotFoundException if data cannot be loaded properly.
     */
    
    @Override
    public void processUserChoice(Doctor doctor, String choice) throws IOException, ClassNotFoundException {
        DoctorView doctorView = new DoctorView();
        DoctorAppointmentView appointmentView = new DoctorAppointmentView();
        DoctorPatientView patientView = new DoctorPatientView();
        switch (choice) {
            case "1": patientView.viewPatientRecord(doctor); break;
            case "2": patientView.updatePatientRecord(); break;
            case "3": doctorView.viewPersonalSchedule(doctor); break;
            case "4": doctorView.setAvailability(doctor); break;
            case "5": appointmentView.acceptOrDeclineAppointment(doctor); break;
            case "6": appointmentView.viewUpcomingAppointments(doctor); break;
            case "7": appointmentView.recordAppointmentOutcome(doctor); break;
            default: System.out.println("Invalid choice. Please try again.");
        }
    }
}
