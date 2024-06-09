package com.data_management;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {
    private static DataStorage dataStorage;
    private ConcurrentHashMap<Integer, Patient> patientMap;

    public DataStorage() {
        this.patientMap = new ConcurrentHashMap<>();
    }

    public static DataStorage getDataStorage() {
        if (dataStorage == null) {
            dataStorage = new DataStorage();
        }
        return dataStorage;
    }

    public Map<Integer, Patient> getPatientMap() {
        return patientMap;
    }

    public Patient getPatient(int patientId) {
        return patientMap.get(patientId);
    }


    /**
     * Adds or updates patient data in the storage.
     * If the patient does not exist, a new Patient object is created and added to
     * the storage.
     * Otherwise, the existing patient's records are updated.
     *
     * @param patientId the unique identifier of the patient
     * @param measurementValue the value of the health metric being recorded
     * @param recordType the type of record, e.g., "HeartRate", "BloodPressure"
     * @param timestamp the time at which the measurement was taken, in milliseconds since the Unix epoch
     */
    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        patientMap.compute(patientId, (id, patient) -> {
            if (patient == null) {
                patient = new Patient(patientId);
            }
            patient.addRecord(measurementValue, recordType, timestamp);
            return patient;
        });
    }

    /**
     * Retrieves a list of PatientRecord objects for a specific patient, filtered by
     * a time range.
     *
     * @param patientId the unique identifier of the patient
     * @param startTime the start of the time range, in milliseconds since the Unix epoch
     * @param endTime the end of the time range, in milliseconds since the Unix epoch
     * @return a list of PatientRecord objects that fall within the specified time range
     */
    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        return patient != null ? patient.getRecords(startTime, endTime) : new ArrayList<>();
    }

    /**
     * Retrieves all patients stored in the data storage.
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }
}
