package com.oocl.web.sampleWebApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

class WebTestUtil {
    public static <T> T toObject(String jsonContent, Class<T> clazz) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, clazz);
    }

    public static <T> T getContentAsObject(MvcResult result, Class<T> clazz) throws Exception {
        return toObject(result.getResponse().getContentAsString(), clazz);

    }
    public static String toJSON(ParkingBoy parkingBoy){
        return "{\"employeeId\": " + "\"" + parkingBoy.getEmployeeId() + "\"" + "}";
    }

    public static String toJSON(String parkingLotId,String capacity, String availablePositionCount){
        return "{\"parkingLotId\": " + "\"" + parkingLotId + "\"" +
                ",\"capacity\": " + "\"" + capacity + "\""+
                ",\"availablePositionCount\": " + "\"" + availablePositionCount + "\""+
                "}";
    }
}
