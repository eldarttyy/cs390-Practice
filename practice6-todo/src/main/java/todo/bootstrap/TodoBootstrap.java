package todo.bootstrap;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import todo.model.TodoItem;
import todo.repo.TodoRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class TodoBootstrap {

    private TodoRepository repo;
    public TodoBootstrap(TodoRepository r) {repo = r;}

    /**
     * Event listener that is called when the server starts.  If there is no data in the todo-item collection,
     * it will create some initial seed data.
     * @param event ignored
     */
    @EventListener
    public void onApplicationStart(ApplicationReadyEvent event) {
        // Do nothing if data already exists, otherwise, initialize the database
        if( repo.count() > 0 ) return;
        initializeDb();
    }

    /**
     * Initialize the database with seed data.
     */
    public void initializeDb() {
        repo.deleteAll();

        // Create and insert a few items.
        System.out.println("Creating initial data...");
        List<TodoItem> items = List.of(
                new TodoItem("Walk Dog", LocalDate.now().plusDays(10) ),
                new TodoItem("Do 390 project work", LocalDate.now().plusDays(1) ),
                new TodoItem("Sleep", LocalDate.now().minusDays(1) )
        );

        repo.saveAll(items);
    }

}
