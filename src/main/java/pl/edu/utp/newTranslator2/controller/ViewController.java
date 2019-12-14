package pl.edu.utp.newTranslator2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/r")
    public String reception() {
        return "rec.html";
    }

    @RequestMapping("/{name}.html")
    public String loadView(@PathVariable("name") String name) {
        return name + ".html";
    }

    @RequestMapping("/{akad}/r")
    public String reception2()
    {
        return "rec2.html";
    }

    @RequestMapping("/{akad}")
    public String index2()
    {
        return "index2.html";
    }
}
