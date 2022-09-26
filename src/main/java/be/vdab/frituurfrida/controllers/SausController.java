package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
@RequestMapping("sauzen")
public class SausController {
/*    private final Saus[] alleSauzen = {
            new Saus(1, "cocktail", new String[]{"tomatenketchup", "mayonaise"}),
            new Saus(2, "mayonaise", new String[]{"eieren", "olie"}),
            new Saus(3, "mosterd", new String[]{"mosterdzaadjes", "mayonaise"}),
            new Saus(4, "tartare", new String[]{"mayonaise", "bearnaise"}),
            new Saus(5, "vinaigrette", new String[]{"olie", "azijn"})
    };*/

    private final SausService sausService;

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SausController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", sausService.findAll());
    }

    private Optional<Saus> findByNummerHelper(long nummer) {
        //return sausService.findAll().stream().filter(saus -> saus.getNummer() == nummer).findFirst();
        return sausService.findById(nummer);
    }

    @GetMapping("{nummer}")
    public ModelAndView findByNummer(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        findByNummerHelper(nummer).ifPresent(gevondenSaus ->
                modelAndView.addObject("saus", gevondenSaus));
        return modelAndView;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet);
    }

    private Stream<Saus> findByLetterHelper(char letter) {
        //return Arrays.stream(alleSauzen).filter(saus -> saus.getNaam().charAt(0) == letter);
        return sausService.findByBeginNaam(letter).stream();
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView findByLetter(@PathVariable char letter) {
        return new ModelAndView("sausAlfabet", "alfabet", alfabet)
                .addObject("sauzen", findByLetterHelper(letter).iterator());
    }
}
