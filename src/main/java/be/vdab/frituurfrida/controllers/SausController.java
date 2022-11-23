package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.forms.SausRadenForm;
import be.vdab.frituurfrida.services.SausService;
import be.vdab.frituurfrida.sessions.SausRaden;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Controller
@RequestMapping("sauzen")
public class SausController {
    private final SausService sausService;
    private final SausRaden sausRaden;
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SausController(SausService sausService, SausRaden sausRaden) {
        this.sausService = sausService;
        this.sausRaden = sausRaden;
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

    @GetMapping("raden")
    public ModelAndView radenForm() {
        var sauzen = sausService.findAll();
        var numberOfSauzen = sauzen.size();
        var randomGetal = new Random().nextInt(numberOfSauzen);
        sausRaden.reset(sauzen.get(randomGetal));
        return new ModelAndView("sausRaden", "sausRaden", sausRaden)
                .addObject(new SausRadenForm(null));
    }

    @PostMapping("raden")
    public ModelAndView raden(@Valid SausRadenForm form, Errors errors) {
        var modelAndView = new ModelAndView("sausRaden");
        if (errors.hasErrors()) {
            return modelAndView.addObject("sausRaden", sausRaden);
        }
        sausRaden.gok(form.letter());
        return modelAndView.addObject("sausRaden", sausRaden);
    }

    @PostMapping("raden/nieuwspel")
    String radenNieuwSpel() {
        return "redirect:/sauzen/raden";
    }
}
