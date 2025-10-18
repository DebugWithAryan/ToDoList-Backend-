package org.aryan.todolistbackend.service;


import org.aryan.todolistbackend.model.ToDo;
import org.aryan.todolistbackend.repo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<ToDo> getAllTodos()
    {
        return todoRepository.findAll();
    }

    public Optional<ToDo> getTodoById(Long id)
    {
        return todoRepository.findById(id);
    }

    public List<ToDo> getTodosByStatus(Boolean completed){
        return todoRepository.findByCompleted(completed);
    }

    public List<ToDo> searchTodos(String title){
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }

    public ToDo createTodo(ToDo toDo){
        return todoRepository.save(toDo);
    }

    public ToDo updateTodo(Long id, ToDo todoDetails){
        ToDo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setTitle(todoDetails.getTitle());
        todo.setCompleted(todoDetails.getCompleted());
        todo.setDescription(todoDetails.getDescription());

        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id){
        ToDo toDo = todoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Todo not found"));

        todoRepository.delete(toDo);
    }

    public ToDo toggleComplete(Long id){
        ToDo toDo = todoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Todo not found"));

        toDo.setCompleted(toDo.getCompleted());

        return todoRepository.save(toDo);
    }
}
