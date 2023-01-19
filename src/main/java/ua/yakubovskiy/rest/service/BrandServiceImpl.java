package ua.yakubovskiy.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.yakubovskiy.rest.dto.BrandDetails;
import ua.yakubovskiy.rest.dto.BrandSave;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.exception.NotFoundException;
import ua.yakubovskiy.rest.repository.BrandRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandRepository brandRepository;

    @Override
    @Transactional
    public int create(BrandSave brandSave) {
        Brand brand = new Brand();
        updateDataFromData(brand, brandSave);
        brandRepository.save(brand);
        return brand.getId();
    }

    @Override
    @Transactional
    public BrandDetails getById(int id) {
        Brand brand = getOrThrow(id);
        return convertToDetails(brand);
    }

    @Override
    @Transactional
    public List<BrandDetails> getAll() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandDetails> brandDetails = new ArrayList<>();
        brands.forEach(brand -> brandDetails.add(convertToDetails(brand)));
        return brandDetails;
    }

    @Override
    @Transactional
    public void update(int id, BrandSave brandSave) {
        Brand brand = getOrThrow(id);
        updateDataFromData(brand, brandSave);
        brandRepository.save(brand);
    }

    @Override
    @Transactional
    public void delete(int id) {
        brandRepository.deleteById(id);
    }

    private Brand getOrThrow(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand with id %d not found".formatted(id)));
    }

    private void updateDataFromData(Brand brand, BrandSave brandSave) {
        brand.setName(brandSave.getName());
    }

    private BrandDetails convertToDetails(Brand data) {
        return BrandDetails.builder()
                .id(data.getId())
                .name(data.getName())
                .build();
    }
}
