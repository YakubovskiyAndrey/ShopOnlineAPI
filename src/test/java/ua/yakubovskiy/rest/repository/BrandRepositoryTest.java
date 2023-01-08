package ua.yakubovskiy.rest.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.yakubovskiy.rest.entity.Brand;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository repository;

    @Test
    void testCreate() {
        Brand brand = new Brand();
        brand.setName("ZTE");
        repository.save(brand);

        Brand found = entityManager.find(Brand.class, brand.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName().trim()).isEqualTo(brand.getName());
    }

    @Test
    void testFindByIdAndDelete() {
        Brand brand = new Brand();
        brand.setName("ZTE");
        entityManager.persistAndFlush(brand);
        Brand found = repository.findById(brand.getId()).orElse(null);
        assertThat(found).isNotNull();

        repository.delete(brand);
        Brand foundDeleted = repository.findById(brand.getId()).orElse(null);
        assertThat(foundDeleted).isNull();
    }

    @Test
    void testFindAll() {
        Brand brandZTE = new Brand();
        brandZTE.setName("ZTE");
        Brand brandMotorola = new Brand();
        brandMotorola.setName("Motorola");
        entityManager.persistAndFlush(brandZTE);
        entityManager.persistAndFlush(brandMotorola);

        List<Brand> brandList = repository.findAll();
        assertThat(brandList).hasSizeGreaterThanOrEqualTo(2);
    }
}