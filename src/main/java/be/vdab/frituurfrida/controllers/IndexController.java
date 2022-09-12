package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Adres;
import be.vdab.frituurfrida.domain.Gemeente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Controller
class IndexController {
    private static final int EEN_JAAR_IN_SECONDEN = 31_536_000;

    @GetMapping("/")
    public ModelAndView index(@CookieValue Optional<Integer> bezoeken, HttpServletResponse response) {
        Cookie cookie;
        var openOfGesloten = LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY) ? "gesloten" : "open";
        var modelAndView = new ModelAndView("index", "openGesloten", openOfGesloten);
        modelAndView.addObject("adres",
                new Adres("Koning Albertstraat", "4",
                        new Gemeente("Nijlen", 2560)));
        //bezoeken.ifPresent(aantal -> modelAndView.addObject("bezoeken", aantal));
        if (bezoeken.isPresent()) {
            modelAndView.addObject("bezoeken", String.valueOf(bezoeken.get() + 1));
            cookie = new Cookie("bezoeken", String.valueOf(bezoeken.get() + 1));
        } else {
            modelAndView.addObject("bezoeken", 1);
            cookie = new Cookie("bezoeken", "1");
        }
        cookie.setMaxAge(EEN_JAAR_IN_SECONDEN);
        cookie.setPath("/");
        response.addCookie(cookie);
        return modelAndView;
    }
}

