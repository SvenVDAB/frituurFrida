package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.repositories.SnackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SnackService {
    private final SnackRepository snackRepository;

    public SnackService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    public Optional<Snack> read(long id) {
        return snackRepository.findById(id);
    }

    @Transactional
    public void update(Snack snack) {
        snackRepository.update(snack);
    }

    public List<Snack> findByBeginNaam(String beginNaam) {
        return snackRepository.findByBeginNaam(beginNaam);
    }
}
