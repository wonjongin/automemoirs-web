package io.github.wonjongin.automemoirsweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
@Controller
public class MainController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
