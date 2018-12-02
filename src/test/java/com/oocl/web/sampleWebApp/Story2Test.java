package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
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
import static org.junit.Assert.assertEquals;
import static com.oocl.web.sampleWebApp.WebTestUtil.toJSON;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Story2Test {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_get_parking_lot() throws Exception {
//         Given
        final String parkingLotJSONString = toJSON("ParkingLotA","40","20");

        final MvcResult Postresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONString))
                .andReturn();

        // When
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/parkinglots"))
                .andReturn();

        // Then
        assertEquals(200, result.getResponse().getStatus());

        assertEquals(1, parkingLotRepository.findAll().size());
        assertEquals("ParkingLotA",parkingLotRepository.findAll().get(0).getParkingLotId());
    }



	@Test
	public void should_add_parking_lots() throws Exception {
        //Given
        final String parkingLotJSONString = toJSON("ParkingLotA","40","20");

        //When
        final MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONString))
                .andReturn();

        // Then
        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    public void should_not_add_parking_lots_with_duplicated_name() throws Exception {
        // Given Two parking Lot
        final String parkingLotJSONString = toJSON("ParkingLotA","40","20");

        final String parkingLotWithDuplicatedNameJSONString = toJSON("ParkingLotA","50","10");

        // When save this parking Lot in repository
        final MvcResult FirstPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONString))
                .andReturn();

        final MvcResult SecondPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotWithDuplicatedNameJSONString))
                .andReturn();

//         Then the repository shoud contain a record
        assertEquals(409, SecondPostresult.getResponse().getStatus());
        assertEquals(1,parkingLotRepository.findAll().size());
    }

    @Test
    public void should_not_add_parking_lots_with_invalid_parameters() throws Exception {
        // Given Two parking Lot
        final String parkingLotJSONStringA = toJSON("ParkingLotA","40","50");
        final String parkingLotJSONStringB = toJSON("ParkingLotA","40","-1");
        final String parkingLotJSONStringC = toJSON("ParkingLotA","-2","-10");

        // When save this parking Lot in repository
        final MvcResult FirstPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONStringA))
                .andReturn();

        final MvcResult SecondPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONStringB))
                .andReturn();

        final MvcResult ThirdPostresult = mvc.perform(MockMvcRequestBuilders
                .post("/parkinglots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(parkingLotJSONStringC))
                .andReturn();

//         Then the repository shoud contain a record
        assertEquals(400, FirstPostresult.getResponse().getStatus());
        assertEquals(400, SecondPostresult.getResponse().getStatus());
        assertEquals(400, ThirdPostresult.getResponse().getStatus());

        assertEquals("Capacity should larger than Available Position Count",
                FirstPostresult.getResponse().getContentAsString());
        assertEquals("Available Position Count should be larger than zero",
                SecondPostresult.getResponse().getContentAsString());
        assertEquals("Capacity should be larger than zero",
                ThirdPostresult.getResponse().getContentAsString());
    }

}
