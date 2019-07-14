package ru.maximum13.jrtest.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ru.maximum13.jax.core.obj.JInt;
import ru.maximum13.jax.core.util.NumberUtils;
import ru.maximum13.jrtest.model.Category;
import ru.maximum13.jrtest.model.Todo;
import ru.maximum13.jrtest.service.SortOrder;
import ru.maximum13.jrtest.service.TodoService;

/**
 *
 * @author MAXIMUM13
 */
@Controller("todo_controller")
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping("/")
    public String loadMainPage() {
        return "todo";
    }

    @ResponseBody
    @RequestMapping("/todos")
    public List<Todo> getTodos(@RequestParam Map<String, String> params) {
        String category = params.getOrDefault("category", Category.ALL.name());
        String sortBy = params.getOrDefault("sortBy", Todo.Property.ID.name());
        String order = params.getOrDefault("order", SortOrder.ASC.name());
        int count = NumberUtils.parseInt(params.get("count"), 10);
        int page = NumberUtils.parseInt(params.get("page"), 1);
        return this.todoService.getTodos(
                Category.getByName(category),
                Todo.Property.getByName(sortBy),
                SortOrder.getByName(order),
                JInt.decrease(page) * count, count
        );
    }

    @ModelAttribute("todos")
    public List<Todo> getAllTodos() {
        return this.todoService.getAllTodos();
    }

    @RequestMapping("/add")
    public List<Todo> addTodo(@RequestParam Map<String, String> params) {
        if (params.containsKey("description")) {
            this.todoService.createTodo(params.get("description"));
        }
        return getTodos(params);
    }

    @RequestMapping("/update")
    public List<Todo> updateTodo(@RequestParam Map<String, String> params) {
        if (params.containsKey("id")) {
            int id = Integer.parseInt(params.get("id"));
            if (params.containsKey("description")) {
                this.todoService.updateTodo(id, params.get("description"));
            }
            if (params.containsKey("done")) {
                this.todoService.updateTodo(id, Boolean.parseBoolean(params.get("done")));
            }
        }
        return getTodos(params);
    }

    @RequestMapping("/delete")
    public List<Todo> deleteTodos(@RequestParam Map<String, String> params) {
        if (params.containsKey("ids")) {
            String ids = params.get("ids");
            StringTokenizer st = new StringTokenizer(ids, ",");
            
            List<Integer> idList = new ArrayList<>(st.countTokens());
            while (st.hasMoreTokens()) {
                idList.add(Integer.parseInt(st.nextToken()));
            }
            this.todoService.deleteTodos(idList);
        }
        return getTodos(params);
    }

    @ResponseBody
    @RequestMapping("/count/{category}")
    public int count(@PathVariable("category") String category) {
        return this.todoService.count(Category.getByName(category));
    }

    @ModelAttribute("appName")
    public String getApplicationName() {
        return this.todoService.getAppName();
    }

    @ModelAttribute("taskName")
    public String getTaskName() {
        return this.todoService.getTaskName();
    }
}
