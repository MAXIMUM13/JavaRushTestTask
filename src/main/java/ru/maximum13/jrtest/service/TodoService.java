package ru.maximum13.jrtest.service;

import java.util.List;
import ru.maximum13.jrtest.model.Todo;

/**
 *
 * @author MAXIMUM13
 */
public interface TodoService {

    public int count();
    
    public int count(Todo.Category category);

    public void createTodo(String description);

    public Todo getTodoById(int id);

    public List<Todo> getAllTodos();

    public List<Todo> getTodos(int count);

    public List<Todo> getTodos(Todo.Category category, int count);

    public List<Todo> getTodos(Todo.Property sortBy, SortOrder order, int count);

    public List<Todo> getTodos(Todo.Category category,
            Todo.Property sortBy, SortOrder order, int count);

    public List<Todo> getTodos(Todo.Property sortBy,
            SortOrder order, int startIndex, int count);

    public List<Todo> getTodos(Todo.Category category, Todo.Property sortBy,
            SortOrder order, int startIndex, int count);

    public void updateTodo(int id, String description);

    public void updateTodo(int id, boolean done);

    public void updateTodo(int id, String description, boolean done);

    public void updateTodo(Todo todo, String description);

    public void updateTodo(Todo todo, boolean done);

    public void updateTodo(Todo todo, String description, boolean done);

    public Todo deleteTodo(int id);

    public void deleteTodo(Todo todo);
    
    public void deleteTodos(List<Integer> ids);
    
    public String getAppName();
    
    public String getTaskName();
}
