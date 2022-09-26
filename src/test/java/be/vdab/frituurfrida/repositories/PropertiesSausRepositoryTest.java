package be.vdab.frituurfrida.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesSausRepositoryTest {
    private PropertiesSausRepository sausRepo;
    private static final Path PATH = Path.of("/data/sauzen.properties");

    @BeforeEach
    void beforeEach() {
        sausRepo = new PropertiesSausRepository();
    }

    @Test
    void erZijnEvenveelSauzenAlsErRegelsZijnInHetPropertiesBestand() throws IOException {
        assertThat(sausRepo.findAll().size()).isEqualTo(Files.readAllLines(PATH).size());
    }

    @Test
    void deEersteSausBevatDeDataVanDeEersteRegelInHetPropertiesBestand() throws IOException {
        var saus1 = sausRepo.findAll().get(0);
        var tempString = "" + saus1.getNummer() + ":" + saus1.getNaam() + "," +
                String.join(",", saus1.getIngredienten());

        assertThat(Files.readAllLines(PATH).toArray()[0]).isEqualTo(tempString);
    }
}
