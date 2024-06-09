package com.alerts.Patterns.Objects;

import com.alerts.Alert;

public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
        // Add any additional initialization for BloodPressureAlert
    }
}