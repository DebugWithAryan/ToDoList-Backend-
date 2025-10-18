package org.aryan.todolistbackend.controller;


import org.aryan.todolistbackend.model.ToDo;
import org.aryan.todolistbackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllTodos(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search
    ){
        List<ToDo> todos;

        if (search != null && !search.isEmpty()){
            todos = todoService.searchTodos(search);
        } else if (completed!=null) {
            todos = todoService.getTodosByStatus(completed);
        }else {
            todos = todoService.getAllTodos();
        }
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable Long id){
        return todoService.getTodoById(id)
                .map(todo -> new ResponseEntity<>(todo,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<ToDo> createTodo(@RequestBody ToDo toDo){
        ToDo createdTodo = todoService.createTodo(toDo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateTodo(@PathVariable Long id, @RequestBody ToDo toDo){
        try {
            ToDo updatedTodo = todoService.updateTodo(id, toDo);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ToDo> toggleComplete(@PathVariable Long id){
        try{
            ToDo updatedTodo = todoService.toggleComplete(id);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ToDo> deleteTodo(@PathVariable Long id){
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}











