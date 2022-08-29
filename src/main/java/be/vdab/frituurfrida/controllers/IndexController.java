package be.vdab.frituurfrida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Controller
class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        var openOfGesloten = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "gesloten" : "open";
        return new ModelAndView("index", "openGesloten", openOfGesloten);
    }
}

