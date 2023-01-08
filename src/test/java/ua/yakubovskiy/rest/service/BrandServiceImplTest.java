package ua.yakubovskiy.rest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.repository.BrandRepository;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandServiceImplTest {

    @TestConfiguration
    static class BrandServiceImplTestContextConfiguration {
        @Bean
        public BrandService brandService() {
            return new BrandServiceImpl();
        }
    }

    @Autowired
    private BrandService brandService;

    @MockBean
    private BrandRepository repository;

    @BeforeEach
    public void setUp() {
        Brand brand = new Brand();
        brand.setName("ZTE");
        repository.save(brand);
        Mockito.when(repository.findById(brand.getId()))
                .thenReturn(Optional.of(brand));
    }

    @Test
    void testCreate() {
        Brand brand = new Brand();
        brand.setName("ZTE");
        int brandId = brandService.create(brand);

        Brand found = brandService.getById(brandId);
        assertThat(found).isNotNull();
        assertThat(found.getName().trim()).isEqualTo(brand.getName());
    }
}