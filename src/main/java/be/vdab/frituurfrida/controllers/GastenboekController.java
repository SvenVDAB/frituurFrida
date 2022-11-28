package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.GastenBoekEntry;
import be.vdab.frituurfrida.forms.GastenBoekEntryForm;
import be.vdab.frituurfrida.services.GastenBoekService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("gastenboek")
public class GastenboekController {
    private final GastenBoekService gastenBoekService;

    public GastenboekController(GastenBoekService gastenBoekService) {
        this.gastenBoekService = gastenBoekService;
    }

    @GetMapping
    public ModelAndView toonGastenboek() {
        return new ModelAndView("gastenboek", "gastenBoekEntries", gastenBoekService.findAll());
    }

    @GetMapping("toevoegen")
    public ModelAndView toonForm() {
        var modelAndView = new ModelAndView("gastenboek",
                "gastenBoekEntryForm", new GastenBoekEntryForm("", ""));
        modelAndView.addObject("gastenBoekEntries", gastenBoekService.findAll());
        return modelAndView;
    }

    @PostMapping("toevoegen")
    public String verwerkForm(@Valid GastenBoekEntryForm form, Errors errors) {
        if (errors.hasErrors()) {
            return "/gastenboek";
        }
        gastenBoekService.create(new GastenBoekEntry(0, form.naam(), LocalDate.now(),
                form.bericht()));
        return "redirect:/gastenboek";
    }

    @PostMapping("verwijderen")
    public String delete(Optional<Long[]> id) {
        id.ifPresent(ourId -> gastenBoekService.verwijder(ourId));
        return "redirect:/gastenboek";
    }
}
