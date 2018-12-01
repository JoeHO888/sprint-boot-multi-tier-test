package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingLot;

import java.util.Objects;

public class ParkingLotResponse {
    private String parkingLotId;

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }


    public static ParkingLotResponse create(String parkingLotId) {
        Objects.requireNonNull(parkingLotId);

        final ParkingLotResponse response = new ParkingLotResponse();
        response.setParkingLotId(parkingLotId);
        return response;
    }

    public static ParkingLotResponse create(ParkingLot entity) {
        return create(entity.getParkingLotId());
    }

    @JsonIgnore
    public boolean isValid() {
        return parkingLotId != null;
    }


}
