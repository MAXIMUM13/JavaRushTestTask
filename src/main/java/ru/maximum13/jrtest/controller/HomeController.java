package ru.maximum13.jrtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.maximum13.jrtest.service.TodoService;

/**
 *
 * @author MAXIMUM13
 */
@Controller("homeController")
public class HomeController {
    
    @Autowired
    private TodoService todoService;

    @RequestMapping("/")
    public String home(Model model) {
        return "index";
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
