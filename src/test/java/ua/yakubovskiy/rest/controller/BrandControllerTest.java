package ua.yakubovskiy.rest.controller;

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
import ua.yakubovskiy.rest.repository.BrandRepository;
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
class BrandControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BrandRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBrand() throws Exception {
        String name = "ZTE";
        String body = """
          {
              "name": "%s"
          }
        """.formatted(name);

        MvcResult mvcResult = mvc.perform(post("/api/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isCreated())
                .andReturn();

        RestResponse response = parseResponse(mvcResult);
        int brandId = Integer.parseInt(response.result());
        assertThat(brandId).isPositive();

        Brand brand = repository.findById(brandId).orElse(null);
        assertThat(brand).isNotNull();
        assertThat(brand.getName().trim()).isEqualTo(name);
        repository.deleteById(brandId);
    }

    @Test
    void testGetBrandById() throws Exception {
        Brand brand = new Brand();
        brand.setName("Nokia");
        repository.save(brand);
        int brandId = brand.getId();

        mvc.perform(get("/api/brands/" + brandId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nokia"));

        repository.deleteById(brandId);
    }

    @Test
    void testDeleteBrand() throws Exception {
        Brand brandNokia = new Brand();
        brandNokia.setName("Nokia");
        repository.save(brandNokia);
        int brandId = brandNokia.getId();

        MvcResult mvcResult = mvc.perform(delete("/api/brands/" + brandId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        RestResponse response = parseResponse(mvcResult);
        assertThat(response.result().trim()).isEqualTo("OK");

        Brand brand = repository.findById(brandId).orElse(null);
        assertThat(brand).isNull();
    }

    @Test
    void testGetAll() throws Exception {
        Brand brandNokia = new Brand();
        brandNokia.setName("Nokia");
        Brand brandPoco = new Brand();
        brandPoco.setName("Poco");

        repository.save(brandPoco);
        repository.save(brandNokia);

        int brandIdNokia = brandNokia.getId();
        int brandIdPoco = brandPoco.getId();

        mvc.perform(get("/api/brands/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));

        repository.deleteById(brandIdNokia);
        repository.deleteById(brandIdPoco);
    }

    @Test
    void testUpdateBrand() throws Exception {
        Brand brand = new Brand();
        brand.setName("Nokia");

        repository.save(brand);

        int brandIdNokia = brand.getId();

        String name = "NokiaL";
        String body = """
          {
              "name": "%s"
          }
        """.formatted(name);

        MvcResult mvcResult = mvc.perform(put("/api/brands/" + brandIdNokia)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andExpect(status().isOk())
                .andReturn();

        RestResponse response = parseResponse(mvcResult);
        assertThat(response.result().trim()).isEqualTo("OK");

        Brand brandUpdated = repository.findById(brandIdNokia).orElse(null);
        assertThat(brandUpdated).isNotNull();
        assertThat(brandUpdated.getName().trim()).isEqualTo(name);
        repository.deleteById(brandIdNokia);
    }

    private RestResponse parseResponse(MvcResult mvcResult) {
        try {
            return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), RestResponse.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error parsing json", e);
        }
    }
}