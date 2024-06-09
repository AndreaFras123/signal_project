package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.io.IOException;

class DataStorageTest {
    private DataStorage storage;

    @BeforeEach
    void setUp() {
        storage = new DataStorage();
    }

    @Test
    void testAddAndGetRecords() {
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
        assertEquals(200.0, records.get(1).getMeasurementValue()); // Validate second record
    }

    @Test
    void testGetRecordsForNonExistentPatient() {
        List<PatientRecord> records = storage.getRecords(2, 1714376789050L, 1714376789051L);
        assertTrue(records.isEmpty()); // Check if no records are retrieved for non-existent patient
    }

    @Test
    void testGetAllPatients() {
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(2, 98.0, "BloodOxygenSaturation", 1714376789050L);

        List<Patient> patients = storage.getAllPatients();
        assertEquals(2, patients.size()); // Check if two patients are retrieved
    }
}
