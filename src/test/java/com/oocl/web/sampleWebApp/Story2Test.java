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

//    @Test
//    public void should_not_add_parking_boys_with_duplicated_name() throws Exception {
//        // Given Two parking Boy
//        final ParkingBoy parkingBoy = new ParkingBoy("April");
//        final String parkingBoyJSONString = toJSON(parkingBoy);
//
//        final ParkingBoy parkingBoyWithDuplicatedName = new ParkingBoy("April");
//        final String parkingBoyWithDuplicatedNameJSONString = toJSON(parkingBoyWithDuplicatedName);
//
//        // When save this parking boy in repository
//        final MvcResult FirstPostresult = mvc.perform(MockMvcRequestBuilders
//                .post("/parkingboys")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(parkingBoyJSONString))
//                .andReturn();
//
//        final MvcResult SecondPostresult = mvc.perform(MockMvcRequestBuilders
//                .post("/parkingboys")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(parkingBoyWithDuplicatedNameJSONString))
//                .andReturn();
//
////         Then the repository shoud contain a record
//        assertEquals(409, SecondPostresult.getResponse().getStatus());
//        assertEquals(1,parkingBoyRepository.findAll().size());
//    }

}
