package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvDataReader implements DataReader {
    private String filePath;

    public CsvDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");
                int patientId = Integer.parseInt(values[0]);
                double measurementValue = Double.parseDouble(values[1]);
                String recordType = values[2];
                long timestamp = Long.parseLong(values[3]);

                // Add data to DataStorage
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
