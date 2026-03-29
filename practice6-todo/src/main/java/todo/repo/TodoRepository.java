package todo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import todo.model.TodoItem;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends MongoRepository<TodoItem, String> { }
