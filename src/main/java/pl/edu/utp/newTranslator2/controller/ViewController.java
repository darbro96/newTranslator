package pl.edu.utp.newTranslator2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("")
    public String index()
    {
        return "index.html";
    }

    @RequestMapping("/r")
    public String reception()
    {
        return "rec.html";
    }
}
