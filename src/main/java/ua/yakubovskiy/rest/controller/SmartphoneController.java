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
import ua.yakubovskiy.rest.dto.RestResponse;
import ua.yakubovskiy.rest.dto.SmartphoneDetails;
import ua.yakubovskiy.rest.dto.SmartphoneQueryDto;
import ua.yakubovskiy.rest.dto.SmartphoneSave;
import ua.yakubovskiy.rest.service.SmartphoneServiceImpl;
import java.util.List;

@RestController
@RequestMapping("/api/smartphones")
@RequiredArgsConstructor
public class SmartphoneController {

    @Autowired
    private final SmartphoneServiceImpl smartphoneService;

    @GetMapping("/{id}")
    public SmartphoneDetails getSmartphone(@PathVariable("id") int id) {
        return smartphoneService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createSmartphone(@RequestBody SmartphoneSave smartphoneSave) {
        int id = smartphoneService.create(smartphoneSave);
        return new RestResponse(String.valueOf(id));
    }

    @GetMapping("/all")
    public List<SmartphoneDetails> getAll() {
        return smartphoneService.getAll();
    }

    @PutMapping("/{id}")
    public RestResponse updateSmartphone(@PathVariable int id, @RequestBody SmartphoneSave smartphoneSave) {
        smartphoneService.update(id, smartphoneSave);
        return new RestResponse("OK");
    }

    @DeleteMapping("/{id}")
    public RestResponse deleteSmartphone(@PathVariable("id") int id) {
        smartphoneService.delete(id);
        return new RestResponse("OK");
    }

    @PostMapping("/_search")
    public List<SmartphoneDetails> search(@RequestBody SmartphoneQueryDto query) {
        return smartphoneService.search(query);
    }
}
