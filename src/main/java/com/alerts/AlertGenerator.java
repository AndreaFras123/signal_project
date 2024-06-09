package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class AlertGenerator {
    private DataStorage dataStorage;
    private List<Alert> alerts;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alerts = new ArrayList<>();
    }


    public List<Alert> getAlerts() {
        return alerts;
    }
    /**
     * Scans all patient data stored in DataStorage to identify conditions that
     * meet predefined alert criteria and triggers alerts accordingly.
     */
    public void scanAllPatients() {
        for (Patient patient : dataStorage.getAllPatients()) {
            evaluateData(patient);
        }
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Retrieve all records for the patient
        List<PatientRecord> records = patient.getRecords();

        // Evaluate specific conditions
        evaluateBloodPressure(records, String.valueOf(patient.getPatientId()));
        evaluateBloodOxygenSaturation(records, String.valueOf(patient.getPatientId()));
        evaluateHypotensiveHypoxemia(records, String.valueOf(patient.getPatientId()));
        // Add more evaluations as needed
    }
    private void evaluateBloodPressure(List<PatientRecord> records, String patientId) {
        double lastSystolic = -1;
        double lastDiastolic = -1;
        int trendCount = 0;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressure")) {
                // Assuming format "systolic/diastolic" is stored as measurementValue in a string
                String[] bp = String.valueOf(record.getMeasurementValue()).split("/");
                if (bp.length != 2) continue; // Skip if the format is incorrect

                double systolic = Double.parseDouble(bp[0]);
                double diastolic = Double.parseDouble(bp[1]);

                // Check for critical threshold
                if (systolic > 180 || systolic < 90 || diastolic > 120 || diastolic < 60) {
                    triggerAlert(new Alert(patientId, "Critical Blood Pressure", record.getTimestamp()));
                }

                // Check for increasing/decreasing trend
                if (lastSystolic != -1 && lastDiastolic != -1) {
                    if (Math.abs(systolic - lastSystolic) > 10 || Math.abs(diastolic - lastDiastolic) > 10) {
                        trendCount++;
                        if (trendCount >= 2) {
                            triggerAlert(new Alert(patientId, "Trend in Blood Pressure", record.getTimestamp()));
                        }
                    } else {
                        trendCount = 0;
                    }
                }

                lastSystolic = systolic;
                lastDiastolic = diastolic;
            }
        }
    }

    private void evaluateBloodOxygenSaturation(List<PatientRecord> records, String patientId) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodOxygenSaturation")) {
                double saturation = record.getMeasurementValue();

                // Low Saturation Alert
                if (saturation < 92) {
                    triggerAlert(new Alert(patientId, "Low Blood Oxygen Saturation", record.getTimestamp()));
                }

                // Rapid Drop Alert (Assuming records are in chronological order)
                // Implement rapid drop detection logic here if needed
            }
        }
    }

    private void evaluateHypotensiveHypoxemia(List<PatientRecord> records, String patientId) {
        boolean lowBP = false;
        boolean lowOxygen = false;
        long timestamp = -1;

        for (PatientRecord record : records) {
            if (record.getRecordType().equals("BloodPressure")) {
                // Assuming format "systolic/diastolic" is stored as measurementValue in a string
                String[] bp = String.valueOf(record.getMeasurementValue()).split("/");
                if (bp.length != 2) continue; // Skip if the format is incorrect

                double systolic = Double.parseDouble(bp[0]);

                if (systolic < 90) {
                    lowBP = true;
                    timestamp = record.getTimestamp();
                }
            } else if (record.getRecordType().equals("BloodOxygenSaturation")) {
                double saturation = record.getMeasurementValue();

                if (saturation < 92) {
                    lowOxygen = true;
                    timestamp = record.getTimestamp();
                }
            }

            if (lowBP && lowOxygen) {
                triggerAlert(new Alert(patientId, "Hypotensive Hypoxemia", timestamp));
                lowBP = false; // Reset after triggering
                lowOxygen = false; // Reset after triggering
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        alerts.add(alert); // Store the alert in the list
        System.out.println(alert.toString());
        // Additional implementation might involve logging the alert or notifying staff
    }
}
