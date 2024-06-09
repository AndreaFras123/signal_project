package com.alerts.Patterns;

import com.alerts.Alert;

public interface AlertFactory {
    Alert createAlert(String patientId, String condition, long timestamp);
}