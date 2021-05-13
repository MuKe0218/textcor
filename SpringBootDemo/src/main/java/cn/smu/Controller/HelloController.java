package cn.smu.Controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Hello")
public class HelloController {

    //@PostMapping("/hello")
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        System.out.println("hello");
        return "hello";
    }
}
