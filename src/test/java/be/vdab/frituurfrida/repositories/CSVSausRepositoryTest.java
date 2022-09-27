package be.vdab.frituurfrida.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@PropertySource("application.properties")
@Import(CSVSausRepository.class)
class CSVSausRepositoryTest {
    private final SausRepository sausRepo;
    private final Path PATH;// = Path.of("/data/sauzen.csv");

    CSVSausRepositoryTest(CSVSausRepository sausRepo, @Value("${CSVSausenPad}") Path path) {
        this.sausRepo = sausRepo;
        PATH = path;
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