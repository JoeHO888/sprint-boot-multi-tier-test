package com.oocl.web.sampleWebApp.Service;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ParkingBoyService {

    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    private Boolean isParkingBoyExist(String employee_id, List<ParkingBoy> parkingBoysList){
        Optional<ParkingBoy> parkingBoy = parkingBoysList.stream().filter(e -> e.getEmployeeId() == employee_id).
                findFirst();
        return parkingBoy.isPresent();
    }

    public void mapParkingLotsToParkingBoy(List<ParkingLot> parkingLotsList, String employee_id,List<ParkingBoy> parkingBoysList){
        if (isParkingBoyExist(employee_id,  parkingBoysList)){
            parkingLotsList.stream().forEach(e -> e.setParkingBoyId(employee_id));
        }
    }

}
