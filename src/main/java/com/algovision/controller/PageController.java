package com.algovision.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class PageController {
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("page", "dashboard");
        return "index";
    }
    @GetMapping("/topic/{topicId}")
    public String topic(@PathVariable String topicId, Model model) {
        model.addAttribute("topicId", topicId);
        return "topic";
    }
    @GetMapping("/progress")
    public String progress(Model model) {
        model.addAttribute("page", "progress");
        return "progress";
    }
    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("page", "settings");
        return "settings";
    }
}
