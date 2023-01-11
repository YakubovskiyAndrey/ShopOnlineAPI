package ua.yakubovskiy.rest.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class SmartphoneDetailsTest {

    @Autowired
    private JacksonTester<SmartphoneDetails> json;

    @Test
    void testSerialize() throws Exception {
        SmartphoneDetails smartphoneDetails = new SmartphoneDetails(2, "Samsung s9", "Red");
        JsonContent<SmartphoneDetails> result = json.write(smartphoneDetails);
        assertThat(result).hasJsonPathNumberValue("$.id");
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Samsung s9");
        assertThat(result).extractingJsonPathStringValue("$.colour").isEqualTo("Red");
    }

    @Test
    public void testDeserialize() throws Exception {
        int id = 2;
        String name = "Samsung s9";
        String colour = "Red";
        String jsonContent = """
          {
              "id": %d,
              "name": "%s",
              "colour": "%s"
          }
        """.formatted(id, name, colour);
        SmartphoneDetails smartphoneDetails = json.parse(jsonContent).getObject();
        assertThat(smartphoneDetails.getId()).isEqualTo(2);
        assertThat(smartphoneDetails.getName()).isEqualTo(name);
        assertThat(smartphoneDetails.getColour()).isEqualTo(colour);
    }
}