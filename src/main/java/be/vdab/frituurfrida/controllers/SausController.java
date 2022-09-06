package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping("sauzen")
public class SausController {
    private final Saus[] alleSauzen = {
            new Saus(1, "cocktail", new String[]{"tomatenketchup", "mayonaise"}),
            new Saus(2, "mayonaise", new String[]{"eieren", "olie"}),
            new Saus(3, "mosterd", new String[]{"mosterdzaadjes", "mayonaise"}),
            new Saus(4, "tartare", new String[]{"mayonaise", "bearnaise"}),
            new Saus(5, "vinaigrette", new String[]{"olie", "azijn"})
    };

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", alleSauzen);
    }

    private Optional<Saus> findByNummerHelper(long nummer) {
        return Arrays.stream(alleSauzen).filter(saus -> saus.getNummer() == nummer).findFirst();
    }

    @GetMapping("{nummer}")
    public ModelAndView findByNummer(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        findByNummerHelper(nummer).ifPresent(gevondenSaus ->
                modelAndView.addObject("saus", gevondenSaus));
        return modelAndView;
    }
}
