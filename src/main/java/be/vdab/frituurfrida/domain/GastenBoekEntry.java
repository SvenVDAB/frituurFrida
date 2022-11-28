package be.vdab.frituurfrida.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class GastenBoekEntry {
    private final long id;
    private final String naam;
    @DateTimeFormat(style="S-")
    private final LocalDate datum;
    private final String bericht;

    public GastenBoekEntry(long id, String naam, LocalDate datum, String bericht) {
        this.id = id;
        this.naam = naam;
        this.datum = datum;
        this.bericht = bericht;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getBericht() {
        return bericht;
    }
}
