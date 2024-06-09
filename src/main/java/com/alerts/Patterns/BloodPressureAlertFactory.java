package com.alerts.Patterns;

import com.alerts.Alert;
import com.alerts.Patterns.Objects.BloodPressureAlert;

public class BloodPressureAlertFactory implements AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new BloodPressureAlert(patientId, condition, timestamp);
    }
}

