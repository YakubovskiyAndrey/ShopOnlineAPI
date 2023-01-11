package ua.yakubovskiy.rest.service;

import ua.yakubovskiy.rest.entity.Brand;
import java.util.List;

public interface BrandService {

    int create(Brand brand);

    Brand getById(int id);

    List<Brand> getAll();

    void update(int id, Brand brand);

    void delete(int id);
}
