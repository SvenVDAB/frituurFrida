package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Saus;
import be.vdab.frituurfrida.repositories.SausRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SausService {
    private final SausRepository sausRepo;

    public SausService(SausRepository sausRepo) {
        this.sausRepo = sausRepo;
    }

    public List<Saus> findAll() {
        return sausRepo.findAll();
    }

    public List<Saus> findByBeginNaam(char letter) {
        return findAll().stream().filter(saus -> saus.getNaam().charAt(0) == letter).toList();
    }

    public Optional<Saus> findById(long id) {
        return findAll().stream().filter(saus -> saus.getNummer() == id).findFirst();
    }
}
