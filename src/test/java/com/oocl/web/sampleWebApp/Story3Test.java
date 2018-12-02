package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
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

import static com.oocl.web.sampleWebApp.WebTestUtil.toJSON;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Story3Test {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_get_parking_boy_with_parking_lots_assigned() throws Exception {
//         Given
        final ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("boy"));

        final String parkingLotJSONString = toJSON("ST","41","2");

        final String assignment = "[\"ParkingLotA\"]";

        final MvcResult addParkingLotresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONString))
                .andReturn();

        final MvcResult addParkingLotToParkingBoy = mvc.perform(MockMvcRequestBuilders
                .post("/parkingboys/boy/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assignment))
                .andReturn();

        // When
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/parkingboys/boy/parkinglots"))
                .andReturn();

        // Then
        assertEquals(200, result.getResponse().getStatus());
    }

    @Test
    public void should_assign_parking_boy_with_parking_lots() throws Exception {
//         Given
        final ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("boy"));

        final String parkingLotJSONString = toJSON("ST","41","2");

        final String assignment = "[\"ParkingLotA\"]";

        final MvcResult addParkingLotresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONString))
                .andReturn();

        final MvcResult addParkingLotToParkingBoy = mvc.perform(MockMvcRequestBuilders
                .post("/parkingboys/boy/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(assignment))
                .andReturn();

        // Then
        assertEquals(201, addParkingLotToParkingBoy.getResponse().getStatus());
    }

}
