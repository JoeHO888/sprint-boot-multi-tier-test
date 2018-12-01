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

@RestController
@RequestMapping("/parkinglots")
public class ParkingLotResource {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> createParkingLot(@RequestBody ParkingLot parkingLot) {
        parkingLotRepository.save(parkingLot);
        parkingLotRepository.flush();

        URI location = URI.create("/parkinglots");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("Header", "Create A Parking Lot");
        return new ResponseEntity<String>("Parking Lot "+parkingLot.getParkingLotId()+" is created",
                responseHeaders, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<ParkingLotResponse[]> getAll() {
        final ParkingLotResponse[] parkinglots = parkingLotRepository.findAll().stream()
            .map(ParkingLotResponse::create)
            .toArray(ParkingLotResponse[]::new);
        return ResponseEntity.ok(parkinglots);
    }


}
