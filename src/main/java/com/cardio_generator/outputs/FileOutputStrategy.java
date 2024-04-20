package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the OutputStrategy to write patient data to files.
 * This strategy creates a file for each type of data and appends new entries to it.
 */

// Renamed class to follow UpperCamelCase naming convention
public class fileOutputStrategy implements OutputStrategy {
    /**
     * Constructs a new FileOutputStrategy with the specified base directory.
     * @throws IOException if an I/O error occurs writing to or creating the file
     * @param baseDirectory the directory where data files will be stored
     */

    // Logger for error messages
    private static final Logger LOGGER = Logger.getLogger(fileOutputStrategy.class.getName());

    // Renamed variable to follow lowerCamelCase naming convention
    private String baseDirectory;

    // Renamed map to follow lowerCamelCase and removed underscore
    public final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    // Constructor with parameter renamed to follow lowerCamelCase naming convention
    public fileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the data to a file designated for the data label.
     * @throws IOException if an I/O error occurs during writing to the file
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String filePath;
        try {
            // Attempting to create directories, if they don't exist
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            // Logging error using logger instead of System.err
            LOGGER.log(Level.SEVERE, "Error creating base directory: ", e);
            return;
        }
        // Set the file path variable with proper naming convention
        filePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file with try-with-resources for automatic resource management
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            // Logging error using logger instead of System.err
            LOGGER.log(Level.SEVERE, "Error writing to file " + filePath + ": ", e);
        }
    }
}