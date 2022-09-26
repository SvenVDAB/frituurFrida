package be.vdab.frituurfrida.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class CSVSausRepositoryTest {
    private SausRepository sausRepo;
    private static final Path PATH = Path.of("/data/sauzen.csv");

    @BeforeEach
    void beforeEach() {
        sausRepo = new CSVSausRepository();
    }

    @Test
    void erZijnEvenveelSauzenAlsErRegelsZijnInHetCSVBestand() throws IOException {
        assertThat(sausRepo.findAll().size()).isEqualTo(Files.readAllLines(PATH).size());
    }

    @Test
    void deEersteSausBevatDeDataVanDeEersteRegelInHetCSVBestand() throws IOException {
        var saus1 = sausRepo.findAll().get(0);
        var tempString = "" + saus1.getNummer() + "," + saus1.getNaam() + "," +
                String.join(",", saus1.getIngredienten());

        assertThat(Files.readAllLines(PATH).toArray()[0]).isEqualTo(tempString);
    }
}