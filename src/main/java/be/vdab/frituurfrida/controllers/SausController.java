package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SausController {
    private final Saus[] alleSauzen = {
            new Saus(1, "cocktail", new String[]{ "tomatenketchup", "mayonaise" }),
            new Saus(2, "mayonaise", new String[]{ "eieren", "olie" }),
            new Saus(3, "mosterd", new String[]{ "mosterdzaadjes", "mayonaise" }),
            new Saus(4, "tartare", new String[]{ "mayonaise", "bearnaise" }),
            new Saus(5, "vinaigrette", new String[]{ "olie", "azijn" })
    };
    @GetMapping("/sauzen")
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", alleSauzen);
    }
}
