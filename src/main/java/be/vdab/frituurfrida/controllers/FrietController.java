package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.sessions.ZoekDeFriet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("frieten")
public class FrietController {
    private final ZoekDeFriet zoekDeFriet;

    public FrietController(ZoekDeFriet zoekDeFriet) {
        this.zoekDeFriet = zoekDeFriet;
    }

    @GetMapping("zoeken")
    public ModelAndView toonDeuren() {
        return new ModelAndView("zoekDeFriet").addObject("deuren", zoekDeFriet.getDeuren());
    }

    @PostMapping("zoeken/opendeur")
    public String openDeur(int index) {
        zoekDeFriet.openDeur(index);
        return "redirect:/frieten/zoeken";
    }

    @PostMapping("zoeken/nieuwspel")
    public String nieuwSpel() {
        zoekDeFriet.reset();
        return "redirect:/frieten/zoeken";
    }
}
