package ua.yakubovskiy.rest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.yakubovskiy.rest.dto.BrandDetails;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.repository.BrandRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Test
    void whenFindById() {
        int id = 999;
        String name = "testBrand";

        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(name);
        Mockito.when(brandRepository.findById(brand.getId())).thenReturn(Optional.of(brand));

        BrandDetails found = brandService.getById(id);
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    void testGetAll() {
        Brand brand1 = new Brand();
        brand1.setId(999);
        brand1.setName("testBrand1");

        Brand brand2 = new Brand();
        brand2.setId(998);
        brand2.setName("testBrand2");

        List<Brand> brands = Arrays.asList(brand1, brand2);

        Mockito.when(brandRepository.findAll()).thenReturn(brands);
        List<BrandDetails> found = brandService.getAll();
        assertThat(found).hasSize(2).extracting(BrandDetails::getName).
                contains(brand1.getName(), brand2.getName());
    }
}