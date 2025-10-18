package org.aryan.todolistbackend.repo;

import org.aryan.todolistbackend.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByCompleted(Boolean completed);

    List<ToDo> findByTitleContainingIgnoreCase(String title);
}
