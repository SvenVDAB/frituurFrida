package be.vdab.frituurfrida.services;

import be.vdab.frituurfrida.domain.GastenBoekEntry;
import be.vdab.frituurfrida.repositories.GastenBoekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GastenBoekService {
    private final GastenBoekRepository gastenBoekRepository;

    public GastenBoekService(GastenBoekRepository gastenBoekRepository) {
        this.gastenBoekRepository = gastenBoekRepository;
    }

    @Transactional
    public long create(GastenBoekEntry entry) {
        return gastenBoekRepository.create(entry);
    }

    public List<GastenBoekEntry> findAll() {
        return gastenBoekRepository.findAll();
    }
}
