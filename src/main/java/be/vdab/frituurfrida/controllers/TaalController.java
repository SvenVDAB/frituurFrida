package be.vdab.frituurfrida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("talen")
public class TaalController {
    @GetMapping
    public ModelAndView nederlands(@RequestHeader("accept-language") String language) {
        var modelAndView = new ModelAndView("talen");
        modelAndView.addObject("nederlands", language.startsWith("nl"));
        return modelAndView;
    }
}
