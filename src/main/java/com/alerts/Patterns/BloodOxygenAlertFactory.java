package com.alerts.Patterns;

import com.alerts.Alert;
import com.alerts.Patterns.Objects.BloodOxygenAlert;

public class BloodOxygenAlertFactory implements AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodOxygenAlert(patientId, condition, timestamp);
    }
}