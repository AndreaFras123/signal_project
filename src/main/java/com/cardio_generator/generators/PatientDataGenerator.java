package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
 * Interface for generating patient data in the simulation.
 * Implementing classes generate different types of health-related data for each patient.
 */

public interface PatientDataGenerator {
    /**
     * Generates data for a single patient and outputs it using the given strategy.
     * 
     * @param patientId the ID of the patient for whom data is being generated
     * @param outputStrategy the strategy for outputting generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
