package com.oocl.web.sampleWebApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParkingBoyResponse {
    private String employeeId;

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    private List<ParkingLot> parkingLots;


    public String getEmployeeId() {
        return employeeId;
    }




    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public static ParkingBoyResponse create(String employeeId) {
        Objects.requireNonNull(employeeId);

        final ParkingBoyResponse response = new ParkingBoyResponse();
        response.setEmployeeId(employeeId);
        return response;
    }

    public static ParkingBoyResponse create(ParkingBoy entity) {
        return create(entity.getEmployeeId());
    }

    public static ParkingBoyResponse getParkingLotsOfParkingBoy(String employeeId, ParkingLotRepository parkingLotRepository){
        Objects.requireNonNull(employeeId);

        final ParkingBoyResponse response = new ParkingBoyResponse();
        response.setEmployeeId(employeeId);

        response.setParkingLots(parkingLotRepository.findAll().
                stream().
                filter(e -> (e.getParkingBoyId() != null && e.getParkingBoyId().equals(employeeId))).
                collect(Collectors.toList()));
        return response;

    }

    @JsonIgnore
    public boolean isValid() {
        return employeeId != null;
    }

}