package dev.wcs.nad.tariffmanager.service;

import dev.wcs.nad.tariffmanager.persistence.entity.Option;
import dev.wcs.nad.tariffmanager.persistence.entity.Tariff;
import dev.wcs.nad.tariffmanager.persistence.repository.OptionRepository;
import dev.wcs.nad.tariffmanager.persistence.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TariffService {

    private final TariffRepository tariffRepository;
    private final OptionRepository optionRepository;

    public TariffService(TariffRepository tariffRepository, OptionRepository optionRepository) {
        this.tariffRepository = tariffRepository;
        this.optionRepository = optionRepository;
    }

    public Iterable<Tariff> readAllTariffsWithPossibleOptions() {
        return tariffRepository.findAll();
    }

    public Tariff assignOptionToTariff(Long tariffId, Long optionId) {
        Tariff tariff = tariffRepository.findById(tariffId).get();
        Option option = optionRepository.findById(optionId).get();
        tariff.getPossibleOptions().add(option);
        return tariffRepository.save(tariff);
    }

    public Optional<Tariff> readTariff(long id) {
        return tariffRepository.findById(id);
    }

    public Tariff createNewTariff(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    public void updateTariff(Tariff existingTariff) {
        tariffRepository.save(existingTariff);
    }

    public void deleteTariff(Long id) {
        tariffRepository.deleteById(id);
    }

    public List<Tariff> readAllTariffsWithName(String name) {
        List<Tariff> tariffs = tariffRepository.findTariffsByNameLikeIgnoreCase("%" + name + "%");
        return tariffs;
    }
}
