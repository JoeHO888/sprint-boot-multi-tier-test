package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotResource {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> createParkingLot(@RequestBody ParkingLot parkingLot) {
        URI location = URI.create("/parkinglots");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("Header", "Create A Parking Lot");
        String body = "";
        HttpStatus status;

        if(parkingLotRepository.findAll().stream().map(e->e.getParkingLotId()).
                collect(Collectors.toList()).contains(parkingLot.getParkingLotId())) {
            body = "Conflict";
            status = HttpStatus.CONFLICT;
        }
        else if (parkingLot.getCapacity()<1){
            body = "Capacity should be larger than zero";
            status = HttpStatus.BAD_REQUEST;
        }
        else if (parkingLot.getAvailablePositionCount()<1){
            body = "Available Position Count should be larger than zero";
            status = HttpStatus.BAD_REQUEST;
        }
        else if (parkingLot.getCapacity()<parkingLot.getAvailablePositionCount() ){
            body = "Capacity should larger than Available Position Count";
            status = HttpStatus.BAD_REQUEST;
        }

        else{
            parkingLotRepository.save(parkingLot);
            parkingLotRepository.flush();
            body = "Parking Lot "+parkingLot.getParkingLotId()+" is created";
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<String>(body, responseHeaders, status);
    }


    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll() {
        final ParkingLotResponse[] parkinglots = parkingLotRepository.findAll().stream()
            .map(ParkingLotResponse::create)
            .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(parkinglots);
    }


}
