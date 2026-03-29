package todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import todo.model.TodoItem;
import todo.repo.TodoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("todo")
public class TodoController {

    private TodoRepository repo;
    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<TodoItem> getAll() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public TodoItem getOne( @PathVariable String id ) {
        Optional<TodoItem> opt = repo.findById(id);
        if( opt.isPresent() ) {
            return opt.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
