package ua.yakubovskiy.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ua.yakubovskiy.rest.dto.BrandDetails;
import ua.yakubovskiy.rest.dto.BrandSave;
import ua.yakubovskiy.rest.dto.RestResponse;
import ua.yakubovskiy.rest.service.BrandService;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    @Autowired
    private final BrandService brandService;

    @GetMapping("/{id}")
    public BrandDetails getBrand(@PathVariable("id") int id) {
        return brandService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createBrand(@RequestBody BrandSave brandSave) {
        int id = brandService.create(brandSave);
        return new RestResponse(String.valueOf(id));
    }

    @GetMapping("/all")
    public List<BrandDetails> getAll() {
        return brandService.getAll();
    }

    @PutMapping("/{id}")
    public RestResponse updateBrand(@PathVariable int id, @RequestBody BrandSave brandSave) {
        brandService.update(id, brandSave);
        return new RestResponse("OK");
    }

    @DeleteMapping("/{id}")
    public RestResponse deleteBrand(@PathVariable("id") int id) {
        brandService.delete(id);
        return new RestResponse("OK");
    }
}
