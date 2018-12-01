package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyResource {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> createParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        parkingBoyRepository.save(parkingBoy);

        URI location = URI.create("/parkingboys");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        responseHeaders.set("Header", "Create A Parking Boy");
        return new ResponseEntity<String>("Parking Boy "+parkingBoy.getEmployeeId()+" is created",
                responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ParkingBoyResponse[]> getAll() {
        final ParkingBoyResponse[] parkingBoys = parkingBoyRepository.findAll().stream()
                .map(ParkingBoyResponse::create)
                .toArray(ParkingBoyResponse[]::new);
        return ResponseEntity.ok(parkingBoys);
    }

//    @PostMapping(value = "/{id}/parkinglots",produces = {"application/json"})
//    public void addParkingLotsToParkingBoy(@RequestBody ParkingLot[] parkingLots) {
//        employeeService.addEmployee(employee);
//    }
}
