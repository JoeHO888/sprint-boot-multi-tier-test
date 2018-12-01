package com.oocl.web.sampleWebApp.Service;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ParkingBoyService {

    public Boolean isParkingBoyExist(String employee_id,ParkingBoyRepository parkingBoyRepository){
        List<ParkingBoy> parkingBoysList = parkingBoyRepository.findAll();
        Optional<ParkingBoy> parkingBoy = parkingBoysList.stream().filter(e -> e.getEmployeeId().equals(employee_id)).
                findFirst();
        return parkingBoy.isPresent();
    }

    public void mapParkingLotsToParkingBoy(List<ParkingLot> parkingLotsList, String employee_id,ParkingLotRepository parkingLotRepository){
        parkingLotsList.stream().forEach(e ->{
                e.setParkingBoyId(employee_id);
                //parkingLotRepository.save(e);

        });

    }
}
