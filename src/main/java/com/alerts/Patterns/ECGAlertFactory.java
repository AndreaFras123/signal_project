package com.alerts.Patterns;

import com.alerts.Alert;
import com.alerts.Patterns.Objects.ECGAlert;

public class ECGAlertFactory implements AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition, long timestamp) {
        return new ECGAlert(patientId, condition, timestamp);
    }
}