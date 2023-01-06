package ua.yakubovskiy.rest.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.yakubovskiy.rest.ShopOnlineApiApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.yakubovskiy.rest.ShopOnlineApiApplication;
import ua.yakubovskiy.rest.dto.RestResponse;
import ua.yakubovskiy.rest.entity.Brand;
import ua.yakubovskiy.rest.entity.Smartphone;
import ua.yakubovskiy.rest.repository.BrandRepository;
import ua.yakubovskiy.rest.repository.SmartphoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ShopOnlineApiApplication.class)
@AutoConfigureMockMvc
class SmartphoneControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SmartphoneRepository repository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Brand testBrand;

    @BeforeEach
    void setUp(){
        testBrand = new Brand();
        testBrand.setName("TestBrand");
        brandRepository.save(testBrand);
    }

    @Test
    void testCreateSmartphone() throws Exception {
        String model = "S20 Ultra";
        String colour = "Red";
        String body = """
          {
              "model": "%s",
              "colour": "%s",
              "brandId": %d
          }               
        """.formatted(model, colour, testBrand.getId());

        MvcResult mvcResult = mvc.perform(post("/api/smartphones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn();

        RestResponse response = parseResponse(mvcResult);
        int smartphoneId = Integer.parseInt(response.result());
        assertThat(smartphoneId).isPositive();

        Smartphone smartphone = repository.findById(smartphoneId).orElse(null);
        assertThat(smartphone).isNotNull();
        assertThat(smartphone.getModel().trim()).isEqualTo(model);
        assertThat(smartphone.getColour().trim()).isEqualTo(colour);
        repository.deleteById(smartphoneId);
    }

    @Test
    void testGetSmartphoneById() throws Exception {
        String model = "S20 Ultra";
        String colour = "Red";

        Smartphone smartphone = new Smartphone();
        smartphone.setModel(model);
        smartphone.setColour(colour);
        smartphone.setBrand(testBrand);

        repository.save(smartphone);

        int smartphoneId = smartphone.getId();

        mvc.perform(get("/api/smartphones/" + smartphoneId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testBrand.getName() + " " + model))
                .andExpect(jsonPath("$.colour").value(colour));

        repository.deleteById(smartphoneId);
    }

    @Test
    void testDeleteSmartphone() throws Exception {
        String model = "S20 Ultra";
        String colour = "Red";

        Smartphone smartphone = new Smartphone();
        smartphone.setModel(model);
        smartphone.setColour(colour);
        smartphone.setBrand(testBrand);

        repository.save(smartphone);

        int smartphoneId = smartphone.getId();

        MvcResult mvcResult = mvc.perform(delete("/api/smartphones/" + smartphoneId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        RestResponse response = parseResponse(mvcResult);
        assertThat(response.result().trim()).isEqualTo("OK");

        Smartphone smartphoneData = repository.findById(smartphoneId).orElse(null);
        assertThat(smartphoneData).isNull();
    }

    private RestResponse parseResponse(MvcResult mvcResult) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RestResponse.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }

    @AfterEach
    void tearDown(){
        brandRepository.deleteById(testBrand.getId());
    }
}