package ua.yakubovskiy.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.yakubovskiy.rest.entity.Smartphone;
import java.util.List;

@Repository
public interface SmartphoneRepository extends JpaRepository<Smartphone, Integer> {

    @Query(value = "SELECT * FROM ps.smartphones where brand_id = ?1 and color = ?2 limit ?3 offset ?4",
    nativeQuery = true)
    List<Smartphone> findByBrand_idAndColor(int brandId, String color, int from, int size);
}
