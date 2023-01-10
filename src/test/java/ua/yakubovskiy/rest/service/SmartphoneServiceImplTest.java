package ua.yakubovskiy.rest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.yakubovskiy.rest.dto.SmartphoneDetails;
import ua.yakubovskiy.rest.dto.SmartphoneQueryDto;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.entity.Smartphone;
import ua.yakubovskiy.rest.repository.SmartphoneRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SmartphoneServiceImplTest {

    @InjectMocks
    private SmartphoneServiceImpl smartphoneService;

    @Mock
    private SmartphoneRepository smartphoneRepository;

    @Test
    void whenFindById() {
        Brand brand = new Brand();
        brand.setName("testBrand");
        brand.setId(922);

        Smartphone smartphone = new Smartphone();
        smartphone.setId(999);
        smartphone.setModel("testPhone");
        smartphone.setColour("green");
        smartphone.setBrand(brand);
        Mockito.when(smartphoneRepository.findById(smartphone.getId())).thenReturn(Optional.of(smartphone));

        SmartphoneDetails found = smartphoneService.getById(smartphone.getId());
        assertThat(found.getId()).isEqualTo(smartphone.getId());
        assertThat(found.getName()).isEqualTo(brand.getName() + " " + smartphone.getModel());
    }

    @Test
    void testGetAll() {
        Brand brand = new Brand();
        brand.setName("testBrand");
        brand.setId(922);

        Smartphone smartphone1 = new Smartphone();
        smartphone1.setId(999);
        smartphone1.setModel("testPhone1");
        smartphone1.setColour("green");
        smartphone1.setBrand(brand);

        Smartphone smartphone2 = new Smartphone();
        smartphone2.setId(997);
        smartphone2.setModel("testPhone2");
        smartphone2.setColour("red");
        smartphone2.setBrand(brand);

        List<Smartphone> smartphones = Arrays.asList(smartphone1, smartphone2);

        Mockito.when(smartphoneRepository.findAll()).thenReturn(smartphones);

        List<SmartphoneDetails> found = smartphoneService.getAll();
        assertThat(found).hasSize(2).extracting(SmartphoneDetails::getName).
                contains(brand.getName() + " " + smartphone1.getModel(),
                        brand.getName() + " " + smartphone2.getModel());
    }

    @Test
    void testSearch() {
        int brandId = 922;
        String colour = "red";
        int from = 0;
        int size = 2;

        Brand brand = new Brand();
        brand.setName("testBrand");
        brand.setId(brandId);

        Smartphone smartphone1 = new Smartphone();
        smartphone1.setId(999);
        smartphone1.setModel("testPhone1");
        smartphone1.setColour(colour);
        smartphone1.setBrand(brand);

        Smartphone smartphone2 = new Smartphone();
        smartphone2.setId(997);
        smartphone2.setModel("testPhone2");
        smartphone2.setColour(colour);
        smartphone2.setBrand(brand);

        SmartphoneQueryDto query = new SmartphoneQueryDto();
        query.setBrandId(brandId);
        query.setColour(colour);
        query.setSize(size);
        query.setFrom(from);

        List<Smartphone> smartphones = Arrays.asList(smartphone1, smartphone2);

        Mockito.when(smartphoneRepository.findByBrandIdAndColour(brandId,
                colour, size, from)).thenReturn(smartphones);

        List<SmartphoneDetails> found = smartphoneService.search(query);
        assertThat(found).hasSize(2).extracting(SmartphoneDetails::getName).
                contains(brand.getName() + " " + smartphone1.getModel(),
                        brand.getName() + " " + smartphone2.getModel());
    }
}