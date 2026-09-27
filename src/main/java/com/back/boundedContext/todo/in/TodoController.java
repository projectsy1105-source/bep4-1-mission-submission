package com.back.boundedContext.todo.in;

import com.back.boundedContext.todo.app.TodoFacade;
import com.back.boundedContext.todo.domain.Todo;
import com.back.boundedContext.todo.dto.TodoDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoFacade todoFacade;

    @GetMapping
    public List<Todo> list() {
        return todoFacade.list();
    }

    @GetMapping("/{id}")
    public Todo get(@PathVariable int id) {
        return todoFacade.find(id);
    }

    @PostMapping
    public int create(@Valid @RequestBody TodoDto dto) {
        return todoFacade.createTodo(dto.memberId(), dto.title());
    }

    @PostMapping("/{id}")
    public void update(@PathVariable int id, @Valid @RequestBody TodoDto dto) {
         todoFacade.update(id, dto.title(), dto.completed());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        todoFacade.deleteTodo(id);
    }
}
