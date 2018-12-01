package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.Service.ParkingBoyService;
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
import java.util.List;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyResource {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingBoyService parkingBoyService;

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> createParkingBoy(@RequestBody ParkingBoy parkingBoy) {
        parkingBoyRepository.save(parkingBoy);
        parkingBoyRepository.flush();

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

//    @PostMapping(value = "/{employee_id}/parkinglots",produces = {"application/json"})
//    public void addParkingLotsToParkingBoy(@RequestBody String employee_id) {
//        List<ParkingBoy> parkingBoysList = parkingBoyRepository
//        parkingBoyService.mapParkingLotsToParkingBoy(,employee_id,);
//    }

}
