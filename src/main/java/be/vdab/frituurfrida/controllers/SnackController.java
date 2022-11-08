package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.exceptions.SnackNietGevondenException;
import be.vdab.frituurfrida.forms.BeginNaamForm;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("snacks")
public class SnackController {
    private final SnackService snackService;
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("alfabet")
    public ModelAndView alfabet() {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView findByLetter(@PathVariable char letter) {
        return new ModelAndView("snackAlfabet", "alfabet", alfabet)
                .addObject("snacks", snackService.findByBeginNaam(String.valueOf(letter)));
    }

    @GetMapping("aantalverkochtesnacksperid")
    public ModelAndView findAantalVerkochteSnacksPerId() {
        return new ModelAndView("aantalverkochtesnacksperid", "aantalVerkochteSnacksPerId",
                snackService.findAantalVerkochteSnacksPerId());
    }

    @GetMapping("beginnaam/form")
    public ModelAndView beginNaamForm() {
        return new ModelAndView("beginnaam")
                .addObject(new BeginNaamForm(null));
    }

    @GetMapping("beginnaam")
    public ModelAndView findByBeginNaam(@Valid BeginNaamForm form, Errors errors) {
        var modelAndView = new ModelAndView("beginnaam");
        if (errors.hasErrors()) return modelAndView;
        return modelAndView.addObject("snacks",
                snackService.findByBeginNaam(form.begin()));
    }

    @GetMapping("wijzigen/form/{id}")
    public ModelAndView wijzigenForm(@PathVariable Long id) {
        var modelAndView = new ModelAndView("wijzigSnack");
        snackService.read(id).ifPresent(snack -> {
            modelAndView.addObject("snack", snack);
        });
        return modelAndView;
    }

    @PostMapping("wijzigen")
    public String wijzigen(@Valid Snack snack, Errors errors,
                           RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            //return new ModelAndView("wijzigSnack");
            return "wijzigSnack";
        }
        try {
            snackService.update(snack);
/*        return new ModelAndView("snackAlfabet",
                "alfabet", alfabet);*/
            //return new ModelAndView("/");
            return "redirect:/";
        } catch (SnackNietGevondenException ex) {
            redirect.addAttribute("snackNietGevonden", snack.getId());
            return "redirect:/";
        }
    }
}
