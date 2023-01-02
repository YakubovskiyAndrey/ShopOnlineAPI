package ua.yakubovskiy.rest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.exception.NotFoundException;
import ua.yakubovskiy.rest.repository.BrandRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService{

    @Autowired
    private final BrandRepository brandRepository;

    @Override
    public int create(Brand brand) {
        brandRepository.save(brand);
        return brand.getId();
    }

    @Override
    public Brand getById(int id) {
        Brand brand = getOrThrow(id);
        brand.setName(brand.getName().trim());
        return brand;
    }

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = brandRepository.findAll();
        brands.forEach(brand -> brand.setName(brand.getName().trim()));
        return brands;
    }

    @Override
    public void update(int id, Brand brand) {
        Brand brandUpdated = getOrThrow(id);
        brandUpdated.setName(brand.getName());
        brandRepository.save(brandUpdated);
    }

    @Override
    public void delete(int id) {
        brandRepository.deleteById(id);
    }

    private Brand getOrThrow(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand with id %d not found".formatted(id)));
    }
}
