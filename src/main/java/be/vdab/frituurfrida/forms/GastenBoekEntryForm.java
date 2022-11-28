package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotBlank;

public record GastenBoekEntryForm(@NotBlank String naam, @NotBlank String bericht) {
}
