package ua.yakubovskiy.rest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.entity.Smartphone;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SmartphoneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SmartphoneRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    private Brand testBrand;

    @BeforeEach
    void setUp(){
        testBrand = new Brand();
        testBrand.setName("TestBrand");
        entityManager.persistAndFlush(testBrand);
    }

    @Test
    void testCreate() {
        Smartphone smartphone = new Smartphone();
        smartphone.setModel("F23");
        smartphone.setColour("Red");
        smartphone.setBrand(testBrand);
        repository.save(smartphone);

        Smartphone found = entityManager.find(Smartphone.class, smartphone.getId());
        assertThat(found).isNotNull();
        assertThat(found.getModel().trim()).isEqualTo(smartphone.getModel());
        assertThat(found.getColour().trim()).isEqualTo(smartphone.getColour());
    }

    @Test
    void testFindByIdAndDelete() {
        Smartphone smartphone = new Smartphone();
        smartphone.setModel("F23");
        smartphone.setColour("Red");
        smartphone.setBrand(testBrand);
        entityManager.persistAndFlush(smartphone);
        Smartphone found = repository.findById(smartphone.getId()).orElse(null);
        assertThat(found).isNotNull();

        repository.delete(smartphone);
        Smartphone foundDeleted = repository.findById(smartphone.getId()).orElse(null);
        assertThat(foundDeleted).isNull();
    }

    @Test
    void testFindAll() {
        Smartphone smartphoneR = new Smartphone();
        smartphoneR.setColour("Red");
        smartphoneR.setModel("R");
        smartphoneR.setBrand(testBrand);

        Smartphone smartphoneS = new Smartphone();
        smartphoneS.setColour("Red");
        smartphoneS.setModel("S");
        smartphoneS.setBrand(testBrand);

        entityManager.persistAndFlush(smartphoneS);
        entityManager.persistAndFlush(smartphoneR);

        List<Smartphone> smartphones = repository.findAll();
        assertThat(smartphones).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindByBrandIdAndColour() {
        String colour = "testColour";

        Smartphone smartphoneS = new Smartphone();
        smartphoneS.setBrand(testBrand);
        smartphoneS.setModel("S20 Ultra");
        smartphoneS.setColour(colour);

        Smartphone smartphoneJ = new Smartphone();
        smartphoneJ.setBrand(testBrand);
        smartphoneJ.setModel("J10");
        smartphoneJ.setColour(colour);

        entityManager.persistAndFlush(smartphoneS);
        entityManager.persistAndFlush(smartphoneJ);

        List<Smartphone> smartphones = repository.findByBrandIdAndColour(testBrand.getId(), colour, 2, 0);
        assertThat(smartphones).hasSizeGreaterThanOrEqualTo(2);
    }
}