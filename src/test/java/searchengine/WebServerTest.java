
package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

import java.io.IOException;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class WebServerTest {
    WebServer server = null;

    // @BeforeAll
    // void setUp() {
    // try {
    // while (server == null) {
    // try {
    // server = new WebServer("data/test-file.txt");
    // } catch (BindException e) {
    // // port in use. Try again
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    // @AfterAll
    // void tearDown() {
    // server.server.stop(0);
    // server = null;
    // }

    @BeforeAll
    void setUp() {
        while (server == null) {
            try {
                server = new WebServer("data/test-file.txt");
            } catch (BindException e) {
                // port in use. Try again
            } catch (IOException e) {
                // Any other IO problem — log and break
                e.printStackTrace();
                break;
            }
        }

        if (server == null) {
            throw new IllegalStateException("WebServer failed to start.");
        }
    }

    @AfterAll
    void tearDown() {
        if (server != null && server.server != null) {
            server.server.stop(0);
        }
        server = null;
    }

    @Test
    void lookupWebServer() {
        String baseURL = String.format("http://localhost:%d/search?q=", server.server.getAddress().getPort());
        String expected1 = "[{\"url\": \"http://page1.com\", \"title\": \"title1\"}, {\"url\": \"http://page2.com\", \"title\": \"title2\"}]";
        String expected2 = "[{\"url\": \"http://page2.com\", \"title\": \"title2\"}, {\"url\": \"http://page1.com\", \"title\": \"title1\"}]";
        String actual = httpGet(baseURL + "word1");
        assertTrue(expected1.equals(actual) || expected2.equals(actual));
        assertEquals("[{\"url\": \"http://page1.com\", \"title\": \"title1\"}]",
                httpGet(baseURL + "word2"));
        assertEquals("[{\"url\": \"http://page2.com\", \"title\": \"title2\"}]",
                httpGet(baseURL + "word3"));
        assertEquals("[]",
                httpGet(baseURL + "word4"));
    }

    private String httpGet(String url) {
        var uri = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder().uri(uri).GET().build();
        try {
            return client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
