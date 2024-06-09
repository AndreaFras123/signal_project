package data_management;

import com.data_management.DataStorage;
import com.data_management.WebSocketClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class WebSocketClientImplTest {

    private WebSocketClientImpl client;
    private DataStorage dataStorage;

    @BeforeEach
    void setUp() throws URISyntaxException {
        dataStorage = Mockito.mock(DataStorage.class);
        URI uri = new URI("ws://localhost:8080");
        client = new WebSocketClientImpl(uri, dataStorage);
    }

    @Test
    void parseAndStoreTest() {
        String message = "1, 1000, Heart Rate, 80.0";
        client.parseAndStore(message);

        verify(dataStorage).addPatientData(1, 80.0, "Heart Rate", 1000);
    }

    @Test
    void parseAndStoreTestWithPercentage() {
        String message = "1, 1000, Oxygen Saturation, 98.0%";
        client.parseAndStore(message);

        verify(dataStorage).addPatientData(1, 98.0, "Oxygen Saturation", 1000);
    }

    @Test
    void parseAndStoreTestWithInvalidMessage() {
        String message = "Invalid Message";
        client.parseAndStore(message);

        Mockito.verifyNoInteractions(dataStorage);
    }
}