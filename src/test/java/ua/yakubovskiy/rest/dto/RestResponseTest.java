package ua.yakubovskiy.rest.dto;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class RestResponseTest {

    @Autowired
    private JacksonTester<RestResponse> json;

    @Test
    void testSerialize() throws Exception {
        RestResponse response = new RestResponse("OK");
        JsonContent<RestResponse> result = json.write(response);
        assertThat(result).hasJsonPathStringValue("$.result");
        assertThat(result).extractingJsonPathStringValue("$.result").isEqualTo("OK");
    }

    @Test
    public void testDeserialize() throws Exception {
        String jsonContent = """
          {
              "result": "%s"
          }
        """.formatted("OK");
        RestResponse response = json.parse(jsonContent).getObject();
        assertThat(response.result()).isEqualTo("OK");
    }
}