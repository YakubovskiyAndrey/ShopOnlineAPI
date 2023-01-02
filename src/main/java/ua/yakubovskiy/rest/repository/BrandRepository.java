package ua.yakubovskiy.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.yakubovskiy.rest.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
