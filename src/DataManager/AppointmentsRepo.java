package DataManager;

import Models.Appointment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The AppointmentsRepo class is responsible for managing the persistence of 
 * Appointment objects. It provides functionality to save, load, and retrieve 
 * a list of appointments using serialization.
 */

public class AppointmentsRepo implements SerializableRepo<List<Appointment>> {

    /**
     * Path to the file where appointments are stored.
     */
    
    private static final String APPOINTMENTS_FILE = "src/DataManager/appointments.txt";

    /**
     * List of Appointment objects.
     */
    
    private List<Appointment> appointments = new ArrayList<>();

    /**
     * Saves the current list of appointments to a file using serialization.
     * If the file does not exist, it will be created.
     *
     * @throws IOException if an I/O error occurs while writing to the file.
     */

    @Override
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(APPOINTMENTS_FILE))) {
            oos.writeObject(appointments);
            //System.out.println("Appointments data saved successfully.");
        }
    }

    /**
     * Loads the list of appointments from a file using deserialization.
     * If the file does not exist or is empty, the appointments list is initialized as empty.
     *
     * @throws IOException if an I/O error occurs while reading the file.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     */

    @Override
    public void loadData() throws IOException, ClassNotFoundException {
        if (new File(APPOINTMENTS_FILE).exists()) {
            if(Files.size(Paths.get(APPOINTMENTS_FILE)) != 0){
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(APPOINTMENTS_FILE))) {
                    appointments = (List<Appointment>) ois.readObject();
                    //System.out.println("Appointments data loaded successfully.");
                }
            }
            else{
                appointments = new ArrayList<>();
            }
        }
    }

    /**
     * Returns the current list of appointments.
     *
     * @return a list of Appointment objects.
     */

    @Override
    public List<Appointment> getData() {
        return appointments;
    }

    /**
     * Sets the list of appointments.
     *
     * @param appointments the list of Appointment objects to be set.
     */

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
