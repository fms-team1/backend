package kg.neobis.fms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/admin/home")
    public String getAdmin() {
        return "Hello admin";
    }

    @GetMapping("/index")
    public String getUser() {
        return "Hello user";
    }
}