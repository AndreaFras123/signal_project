package data_management;
import com.cardio_generator.outputs.TcpOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.WebSocketClientImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ErrorHandlingTest {

    @Test
    void testWebSocketConnectionError() throws URISyntaxException {

        DataStorage dataStorage = mock(DataStorage.class);
        URI serverUri = new URI("ws://localhost:8080");
        WebSocketClientImpl client = spy(new WebSocketClientImpl(serverUri, dataStorage));

        doThrow(new RuntimeException("Connection error")).when(client).connect();


        assertThrows(RuntimeException.class, () -> client.connectToWebSocket("ws://localhost:8080"));
    }

    @Test
    void testTcpConnectionError() {

        TcpOutputStrategy tcpOutputStrategy = spy(new TcpOutputStrategy(8080));

        doThrow(new RuntimeException("Connection error")).when(tcpOutputStrategy).output(anyInt(), anyLong(), anyString(), anyString());

        assertThrows(RuntimeException.class, () -> tcpOutputStrategy.output(1, 1000, "Heart Rate", "80.0"));
    }

    @Test
    void testInvalidDataError() throws URISyntaxException {

        DataStorage dataStorage = mock(DataStorage.class);
        URI mockUri = new URI("ws://localhost:8080"); // Create a mock URI
        WebSocketClientImpl client = new WebSocketClientImpl(mockUri, dataStorage); // Pass the mock URI


        assertDoesNotThrow(() -> client.parseAndStore("Invalid data"));
    }
}