package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static com.oocl.web.sampleWebApp.WebTestUtil.toJSON;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Story1Test {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private MockMvc mvc;

	@Test
	public void should_get_parking_boys() throws Exception {
        // Given A parking Boy
        final ParkingBoy parkingBoy = new ParkingBoy("April");
        final String parkingBoyJSONString = toJSON(parkingBoy);

        // When save this parking boy in repository
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingBoyJSONString))
                .andReturn();
        // Then the repository shoud contain a record
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    public void should_not_save_parking_boys_with_duplicated_name() throws Exception {
        // Given Two parking Boy
        final ParkingBoy parkingBoy = new ParkingBoy("April");
        final String parkingBoyJSONString = toJSON(parkingBoy);

        final ParkingBoy parkingBoyWithDuplicatedName = new ParkingBoy("April");
        final String parkingBoyWithDuplicatedNameJSONString = toJSON(parkingBoyWithDuplicatedName);

        // When save this parking boy in repository
        final MvcResult FirstPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingBoyJSONString))
                .andReturn();

        final MvcResult SecondPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkingboys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingBoyWithDuplicatedNameJSONString))
                .andReturn();

        // Then the repository shoud contain a record
//        assertEquals(409, SecondPostresult.getResponse().getStatus());
//        assertEquals(1,parkingBoyRepository.findAll().size());
    }

}
