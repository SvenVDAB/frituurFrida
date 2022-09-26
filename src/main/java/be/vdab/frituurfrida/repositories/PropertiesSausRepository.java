package be.vdab.frituurfrida.repositories;

import be.vdab.frituurfrida.exceptions.SausRepositoryException;
import be.vdab.frituurfrida.domain.Saus;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
@Component
@Primary
public class PropertiesSausRepository implements SausRepository {
    private static final Path PAD = Path.of("/data/sauzen.properties");
    @Override
    public List<Saus> findAll() {
        List<Saus> sauzen = new ArrayList<>();

        try {
            var lijnen = Files.readAllLines(PAD);
            for (var lijn : lijnen) {
                var saus = new Saus(
                        Long.parseLong(lijn.split(":")[0]),
                        lijn.split(":")[1].split(",")[0],
                        lijn.split(":")[1].split(",", 2)[1].split(",")
                );
                sauzen.add(saus);
            }
        } catch (IOException ex) {
            throw new SausRepositoryException("Problemen inlezen sauzen.properties", ex);
        }
        return sauzen;
    }
}
