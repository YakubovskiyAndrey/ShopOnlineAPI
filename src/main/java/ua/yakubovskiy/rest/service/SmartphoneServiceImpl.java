package ua.yakubovskiy.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.yakubovskiy.rest.dto.SmartphoneQueryDto;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.entity.Smartphone;
import ua.yakubovskiy.rest.dto.SmartphoneDetails;
import ua.yakubovskiy.rest.dto.SmartphoneSave;
import ua.yakubovskiy.rest.exception.NotFoundException;
import ua.yakubovskiy.rest.repository.BrandRepository;
import ua.yakubovskiy.rest.repository.SmartphoneRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmartphoneServiceImpl implements SmartphoneService{

    @Autowired
    private SmartphoneRepository smartphoneRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    @Transactional
    public int create(SmartphoneSave smartphoneSave) {
        Smartphone smartphone = new Smartphone();
        updateDataFromData(smartphone, smartphoneSave);
        smartphoneRepository.save(smartphone);
        return smartphone.getId();
    }

    @Override
    @Transactional
    public SmartphoneDetails getById(int id){
        Smartphone smartphone = getOrThrow(id);
        return convertToDetails(smartphone);
    }

    @Override
    @Transactional
    public List<SmartphoneDetails> getAll() {
        List<Smartphone> smartphones = smartphoneRepository.findAll();
        List<SmartphoneDetails> smartphoneDetailsList = new ArrayList<>();
        smartphones.forEach(smartphone -> smartphoneDetailsList.add(convertToDetails(smartphone)));
        return smartphoneDetailsList;
    }

    @Override
    @Transactional
    public List<SmartphoneDetails> search(SmartphoneQueryDto query) {
        Pageable pageRequest = PageRequest.of(query.getFrom(), query.getSize(), Sort.by("id"));
        List<Smartphone> smartphones = smartphoneRepository.findByBrandIdAndColour(query.getBrandId(),
                query.getColour(), pageRequest);
        List<SmartphoneDetails> smartphoneDetailsList = new ArrayList<>();
        smartphones.forEach(smartphone -> smartphoneDetailsList.add(convertToDetails(smartphone)));
        return smartphoneDetailsList;
    }

    @Override
    @Transactional
    public void update(int id, SmartphoneSave smartphoneSave) {
        Smartphone smartphone = getOrThrow(id);
        updateDataFromData(smartphone, smartphoneSave);
        smartphoneRepository.save(smartphone);
    }

    @Override
    @Transactional
    public void delete(int id) {
        smartphoneRepository.deleteById(id);
    }

    private void updateDataFromData(Smartphone smartphone, SmartphoneSave smartphoneSave) {
        smartphone.setColour(smartphoneSave.getColour());
        smartphone.setModel(smartphoneSave.getModel());
        smartphone.setBrand(resolveBrand(smartphoneSave.getBrandId()));
    }

    private Brand resolveBrand(Integer brandId) {
        if (brandId == null) {
            return null;
        }
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand with id %d not found".formatted(brandId)));
    }

    private Smartphone getOrThrow(int id) {
        return smartphoneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Phone with id %d not found".formatted(id)));
    }

    private SmartphoneDetails convertToDetails(Smartphone data) {
        return SmartphoneDetails.builder()
                .id(data.getId())
                .name(data.getBrand().getName()+" "+data.getModel())
                .colour(data.getColour())
                .build();
    }
}
