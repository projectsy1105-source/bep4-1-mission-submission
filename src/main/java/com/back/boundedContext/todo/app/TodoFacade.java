package com.back.boundedContext.todo.app;

import com.back.boundedContext.member.domain.Member;
import com.back.boundedContext.member.out.MemberRepository;
import com.back.boundedContext.todo.domain.Todo;
import com.back.boundedContext.todo.out.TodoRepository;
import com.back.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoFacade {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public List<Todo> list() {
        return todoRepository.findAll();
    }

    public Todo find(Integer id) {
        return findTodoById(id);
    }

    public int createTodo(int memberId, String title) {
        Member member = memberRepository.findById(memberId).get();

        if (title == null || title.isEmpty() || title.length() > 20) {
            throw new DomainException("400", "타이틀이 없거나, 너무깁니다.");
        }

        Todo todo = todoRepository.save(new Todo(member, title));
        return todo.getId();
    }

    public void update(int todoId, String title, Boolean completed) {
        Todo todo = findTodoById(todoId);

        if (title != null && !title.isEmpty()) {
            todo.changeTitle(title);
        }

        if (completed != null) {
            todo.changeDone(completed);
        }
    }

    public void deleteTodo(int todoId) {
        Todo todo = findTodoById(todoId);

        todo.delete();
    }

    private Todo findTodoById(int id) {
        return todoRepository.findById(id).orElseThrow(() -> new DomainException("404-1", "없는 todo 입니다."));
    }

}
