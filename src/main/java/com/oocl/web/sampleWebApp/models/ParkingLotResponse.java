package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingLot;

import java.util.Objects;

public class ParkingLotResponse {

    private String parkingLotId;
    private Integer capacity;
    private Integer availablePositionCount;

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailablePositionCount() {
        return availablePositionCount;
    }

    public void setAvailablePositionCount(Integer availablePositionCount) {
        this.availablePositionCount = availablePositionCount;
    }



//    public static ParkingLotResponse create(String parkingLotId) {
//        Objects.requireNonNull(parkingLotId);
//
//        final ParkingLotResponse response = new ParkingLotResponse();
//        response.setParkingLotId(parkingLotId);
//
//        return response;
//    }

    public static ParkingLotResponse create(ParkingLot entity) {
        String parkingLotId = entity.getParkingLotId();
        Integer capacity = entity.getCapacity();
        Integer availablePositionCount = entity.getAvailablePositionCount();


        Objects.requireNonNull(parkingLotId);

        final ParkingLotResponse response = new ParkingLotResponse();
        response.setParkingLotId(parkingLotId);
        response.setCapacity(capacity);
        response.setAvailablePositionCount(availablePositionCount);


        return response;
    }

    @JsonIgnore
    public boolean isValid() {
        return parkingLotId != null;
    }


}
