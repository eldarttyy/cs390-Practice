package todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import todo.bootstrap.TodoBootstrap;
import todo.model.TodoItem;
import todo.repo.TodoRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TodoTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TodoRepository repo;

    @Autowired
    private TodoBootstrap bootstrap;

    private HttpClient client = HttpClient.newHttpClient();

    private List<TodoItem> items;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        // Start with a clean database
        bootstrap.initializeDb();
        items = repo.findAll();
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    @Test
    public void getATodoItem() throws Exception {
        TodoItem testItem = items.get(0);
        String id = testItem.getId();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/todo/" + id ) )
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString() );

        // Expect a 200 response code
        assertEquals(200, response.statusCode());

        // Convert JSON response to TodoItem
        TodoItem responseItem = mapper.readValue(response.body(), TodoItem.class);

        // Check contents
        assertEquals(testItem.getDescription(), responseItem.getDescription());
        assertEquals(testItem.getDue(), responseItem.getDue());
        assertEquals(testItem.isDone(), responseItem.isDone());
    }



}
