package data_management;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(1);
    }

    @Test
    void addRecord() {
        patient.addRecord(120.0, "BloodPressure", 1627849923000L);
        List<PatientRecord> records = patient.getRecords();
        assertEquals(1, records.size());
        assertEquals("BloodPressure", records.get(0).getRecordType());
        assertEquals(120.0, records.get(0).getMeasurementValue());
        assertEquals(1627849923000L, records.get(0).getTimestamp());
    }

    @Test
    void getRecordsInRange() {
        patient.addRecord(120.0, "BloodPressure", 1627849923000L);
        patient.addRecord(125.0, "BloodPressure", 1627849924000L);
        List<PatientRecord> records = patient.getRecords(1627849923000L, 1627849924000L);
        assertEquals(2, records.size());
    }

    @Test
    void getAllRecords() {
        patient.addRecord(120.0, "BloodPressure", 1627849923000L);
        patient.addRecord(98.0, "BloodOxygenSaturation", 1627849923000L);
        List<PatientRecord> records = patient.getRecords();
        assertEquals(2, records.size());
    }
}
