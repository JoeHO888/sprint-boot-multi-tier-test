package com.oocl.web.sampleWebApp.controllers;

import com.oocl.web.sampleWebApp.Service.ParkingBoyService;
import com.oocl.web.sampleWebApp.Service.ParkingLotService;
import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parkingboys")
public class ParkingBoyResource {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingBoyService parkingBoyService;

    @Autowired
    private ParkingLotService parkingLotService;

    private EntityManager entityManager;



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

    @PostMapping(value = "/{employee_id}/parkinglots",produces = {"application/json"})
    public ResponseEntity<String> addParkingLotsToParkingBoy(@RequestBody List<String> parkingLotsList,
                                           @PathVariable String employee_id) {

        Boolean parkingLotListIsValid = parkingLotService.isParkingLotsListValid(parkingLotsList,parkingLotRepository);
        Boolean parkingBoyExists = parkingBoyService.isParkingBoyExist(employee_id, parkingBoyRepository);
        if(parkingLotListIsValid && parkingBoyExists){
            List<ParkingLot>  parkingLotsSelected = parkingLotService.retrieveParkingLots(parkingLotsList,parkingLotRepository);
            List<Long> parkingLotsSelectedID = parkingLotsSelected.stream().map(e -> e.getId()).collect(Collectors.toList());
            parkingLotService.assignParkingLotsToParkingBoy(parkingLotsSelectedID,employee_id,parkingLotRepository);
            return ResponseEntity.ok("123");
        }
        return ResponseEntity.ok("456");

    }
}
