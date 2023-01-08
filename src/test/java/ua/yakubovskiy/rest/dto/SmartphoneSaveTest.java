package ua.yakubovskiy.rest.dto;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class SmartphoneSaveTest {

    @Autowired
    private JacksonTester<SmartphoneSave> json;

    @Test
    void testSerialize() throws Exception {
        SmartphoneSave smartphoneSave = new SmartphoneSave( "Samsung s9", "Red", 2);
        JsonContent<SmartphoneSave> result = json.write(smartphoneSave);
        assertThat(result).hasJsonPathNumberValue("$.brandId");
        assertThat(result).extractingJsonPathNumberValue("$.brandId").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.model").isEqualTo("Samsung s9");
        assertThat(result).extractingJsonPathStringValue("$.colour").isEqualTo("Red");
    }

    @Test
    public void testDeserialize() throws Exception {
        int id = 2;
        String model = "Samsung s9";
        String colour = "Red";
        String jsonContent = """
          {
              "brandId": %d,
              "model": "%s",
              "colour": "%s"
          }
        """.formatted(id, model, colour);
        SmartphoneSave smartphoneSave = json.parse(jsonContent).getObject();
        assertThat(smartphoneSave.getBrandId()).isEqualTo(2);
        assertThat(smartphoneSave.getModel()).isEqualTo(model);
        assertThat(smartphoneSave.getColour()).isEqualTo(colour);
    }
}