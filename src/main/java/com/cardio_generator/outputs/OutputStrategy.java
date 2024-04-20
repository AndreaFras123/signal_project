package com.cardio_generator.outputs;
/**
 * Interface defining the strategy for outputting generated patient data.
 * Implementing classes handle the specifics of data output, such as writing to a file or sending over a network.
 */
public interface OutputStrategy {
    /**
     * Outputs the specified data for a patient.
     * 
     * @param patientId the ID of the patient whose data is being output
     * @param timestamp the timestamp of the data generation
     * @param label a label describing the type of data
     * @param data the actual data to output
     */
    void output(int patientId, long timestamp, String label, String data);
}
