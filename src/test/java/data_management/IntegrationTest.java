package data_management;

import com.alerts.AlertGenerator;
import com.alerts.Alert;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.WebSocketClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IntegrationTest {

    private WebSocketClientImpl client;
    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    void setUp() throws URISyntaxException {
        dataStorage = mock(DataStorage.class);
        URI uri = new URI("ws://localhost:8080");
        client = new WebSocketClientImpl(uri, dataStorage);
        alertGenerator = new AlertGenerator(dataStorage);

        // Create a mock Patient object
        Patient patient = mock(Patient.class);

        // Tell Mockito to return the mock Patient when getPatient(1) is called
        Mockito.when(dataStorage.getPatient(1)).thenReturn(patient);
    }

    @Test
    void testIntegration() {
        // Test multiple messages and different message formats
        String message1 = "1, 1000, Heart Rate, 80.0";
        String message2 = "1, 2000, Blood Pressure, 190.0"; // This should trigger an alert
        String message3 = "1, 3000, Oxygen Saturation, 88.0%"; // This should trigger an alert

        client.onMessage(message1);
        client.onMessage(message2);
        client.onMessage(message3);

        // Call evaluateData to check the alert generation logic
        alertGenerator.evaluateData(dataStorage.getPatient(1));

    }

    void handleInvalidMessage(String invalidMessage) {
        client.onMessage(invalidMessage);
        verifyNoInteractions(dataStorage);
    }

    @Test
    void testValidData() throws URISyntaxException {


        DataStorage dataStorage = mock(DataStorage.class);
        URI mockUri = new URI("ws://localhost:8080"); // Create a mock URI
        WebSocketClientImpl client = new WebSocketClientImpl(mockUri, dataStorage); // Pass the mock URI
        assertDoesNotThrow(() -> client.parseAndStore("1, 1000, Heart Rate, 80.0"));
    }
}