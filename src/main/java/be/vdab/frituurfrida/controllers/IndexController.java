package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
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
        var modelAndView = new ModelAndView("index", "openGesloten", openOfGesloten);
        modelAndView.addObject("adres",
                new Adres("Koning Albertstraat", "4",
                        new Gemeente("Nijlen", 2560)));
        return modelAndView;
    }
}

