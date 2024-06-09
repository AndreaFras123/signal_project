package com.alerts.Patterns.Objects;

import com.alerts.Alert;

public class ECGAlert extends Alert {
    public ECGAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
        // Add any additional initialization for ECGAlert
    }
}