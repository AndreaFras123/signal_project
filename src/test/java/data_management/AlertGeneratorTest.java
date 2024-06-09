package data_management;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest {
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);
    }

    @Test
    void evaluateDataForCriticalBloodPressure() {
        Patient patient = new Patient(1);
        patient.addRecord(190.0, "BloodPressure", 1627849923000L); // Add blood pressure record
        patient.addRecord(110.0, "BloodPressure", 1627849923000L); // Add blood pressure record
        dataStorage.addPatientData(1, 190.0, "BloodPressure", 1627849923000L);
        dataStorage.addPatientData(1, 110.0, "BloodPressure", 1627849923000L);
        alertGenerator.evaluateData(patient);

        // Assuming you have a mechanism to collect or check triggered alerts
    }

    @Test
    void evaluateDataForLowBloodOxygenSaturation() {
        Patient patient = new Patient(1);
        patient.addRecord(88.0, "BloodOxygenSaturation", 1627849923000L);
        dataStorage.addPatientData(1, 88.0, "BloodOxygenSaturation", 1627849923000L);
        alertGenerator.evaluateData(patient);

        // Assuming you have a mechanism to collect or check triggered alerts
    }

    @Test
    void evaluateDataForHypotensiveHypoxemia() {
        Patient patient = new Patient(1);
        patient.addRecord(85.0, "BloodPressure", 1627849923000L); // Add systolic blood pressure record
        patient.addRecord(60.0, "BloodPressure", 1627849923000L); // Add diastolic blood pressure record
        patient.addRecord(90.0, "BloodOxygenSaturation", 1627849923000L);
        dataStorage.addPatientData(1, 85.0, "BloodPressure", 1627849923000L);
        dataStorage.addPatientData(1, 60.0, "BloodPressure", 1627849923000L);
        dataStorage.addPatientData(1, 90.0, "BloodOxygenSaturation", 1627849923000L);
        alertGenerator.evaluateData(patient);

        // Assuming you have a mechanism to collect or check triggered alerts
    }
}
