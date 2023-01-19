package ua.yakubovskiy.rest.service;

import ua.yakubovskiy.rest.dto.BrandDetails;
import ua.yakubovskiy.rest.dto.BrandSave;
import java.util.List;

public interface BrandService {

    int create(BrandSave brandSave);

    BrandDetails getById(int id);

    List<BrandDetails> getAll();

    void update(int id, BrandSave brandSave);

    void delete(int id);
}
