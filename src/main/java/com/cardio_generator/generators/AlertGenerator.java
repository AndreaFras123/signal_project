package com.cardio_generator.generators;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.cardio_generator.outputs.OutputStrategy;
/**
 * Generates alert signals for patient data.
 * This class simulates conditions that trigger health alerts and passes them to the output strategy.
 */

// Class to generate alerts for patient data
public class AlertGenerator implements PatientDataGenerator {
    /**
     * Constructs an AlertGenerator for the given number of patients.
     * 
     * @param patientCount the number of patients to monitor for alerts
     */

    // Using a logger instead of System.err
    private static final Logger LOGGER = Logger.getLogger(AlertGenerator.class.getName());
    
    // Renamed variable to follow lowerCamelCase and made private for encapsulation
    private static final Random randomGenerator = new Random();
    
    // Encapsulating the state array and following lowerCamelCase naming convention
    private boolean[] alertStates; // Indicates resolution state of the alert: false = resolved, true = pressed

    // Constructor to initialize the alert states
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }
    /**
     * Checks and updates the alert state for a patient, generating alert data if necessary.
     */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                // If alert is active, there's a 90% chance to resolve it
                if (randomGenerator.nextDouble() < 0.9) {
                    alertStates[patientId] = false;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Setting the average rate of alerts per period
                double averageAlertRate = 0.1;
                // Calculating the probability of at least one alert in the period
                double alertProbability = -Math.expm1(-averageAlertRate);
                boolean alertTriggered = randomGenerator.nextDouble() < alertProbability;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            // Logging error with proper error handling
            LOGGER.log(Level.SEVERE, "Error in generating alert for patient ID " + patientId, e);
        }
    }

    // Getter method for alert states for better encapsulation
    public boolean getAlertState(int patientId) {
        return alertStates[patientId];
    }
}