package ru.maximum13.jrtest.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maximum13.jrtest.model.*;
import ru.maximum13.jrtest.service.*;

/**
 *
 * @author MAXIMUM13
 */
@Controller("todoMapping")
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @RequestMapping("/")
    public String loadMainPage() {
        return "todo";
    }

    @ResponseBody
    @RequestMapping("/todos")
    public List<Todo> getTodos(@RequestParam Map<String, String> params) {
        String category = params.getOrDefault("category", Todo.Category.ALL.name);
        String sortBy = params.getOrDefault("sortBy", Todo.Property.ID.name);
        String order = params.getOrDefault("order", SortOrder.ASC.name);
        int count = Integer.parseInt(params.getOrDefault("count", "10"));
        int page = Integer.parseInt(params.getOrDefault("page", "1"));
        return this.todoService.getTodos(
                Todo.Category.getByName(category),
                Todo.Property.getByName(sortBy),
                SortOrder.getByName(order),
                (--page) * count, count
        );
    }

    @ModelAttribute("todos")
    public List<Todo> getAllTodos() {
        return this.todoService.getAllTodos();
    }

    @RequestMapping("/add")
    public void addTodo(@RequestParam Map<String, String> params) {
        if (params.containsKey("description")) {
            this.todoService.createTodo(params.get("description"));
        }
    }

    @RequestMapping("/update")
    public void updateTodo(@RequestParam Map<String, String> params) {
        if (params.containsKey("id")) {
            int id = Integer.parseInt(params.get("id"));
            if (params.containsKey("description")) {
                this.todoService.updateTodo(id, params.get("description"));
            }
            if (params.containsKey("done")) {
                this.todoService.updateTodo(id, Boolean.parseBoolean(params.get("done")));
            }
        }
    }

    @RequestMapping("/delete")
    public void deleteTodos(@RequestParam Map<String, String> params) {
        if (params.containsKey("ids")) {
            String ids = params.get("ids");
            StringTokenizer st = new StringTokenizer(ids, ",");
            
            List<Integer> idList = new ArrayList<>(st.countTokens());
            while (st.hasMoreTokens()) {
                idList.add(Integer.parseInt(st.nextToken()));
            }
            this.todoService.deleteTodos(idList);
        }
    }

    @ResponseBody
    @RequestMapping("/count/{category}")
    public int count(@PathVariable("category") String category) {
        return this.todoService.count(Todo.Category.getByName(category));
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
