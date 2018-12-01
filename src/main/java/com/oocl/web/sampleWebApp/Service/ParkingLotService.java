package com.oocl.web.sampleWebApp.Service;

import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {

    public Boolean isParkingLotsListValid(List<String> parkingLotsList, ParkingLotRepository parkingLotRepository){
        List<ParkingLot> parkingLotsRepositoryList = parkingLotRepository.findAll();

        List<String> parkingLotsRepositoryNameList = parkingLotsRepositoryList.
                stream().map(e->e.getParkingLotId()).collect(Collectors.toList());

        Set<String> parkingLotsSet = new HashSet<String>(parkingLotsList);
        Set<String> parkingLotsRepositoryNameSet = new HashSet<String>(parkingLotsRepositoryNameList);

        return parkingLotsRepositoryNameSet.containsAll(parkingLotsSet);
    }
    public List<ParkingLot> retrieveParkingLots(List<String> parkingLotsList,ParkingLotRepository parkingLotRepository){
        List<ParkingLot> parkingLotRepositorylist = parkingLotRepository.findAll();
        List<ParkingLot> parkingLotsSelected = new ArrayList<>();
        for (String element : parkingLotsList) {
            parkingLotsSelected.add(parkingLotRepositorylist.stream().filter(e->e.getParkingLotId().equals(element)).findFirst().get());
        }

        return parkingLotsSelected;
    }

    public void assignParkingLotsToParkingBoy (List<Long> parkingLotsID,String employee_id, ParkingLotRepository parkingLotRepository){
        parkingLotRepository.findAll().stream().forEach(e -> {
            e.setParkingBoyId(null);
            parkingLotRepository.save(e);
        });
        parkingLotsID.stream().forEach(e ->{
            ParkingLot parkingLot = parkingLotRepository.getOne(e);
            parkingLot.setParkingBoyId(employee_id);
            parkingLotRepository.save(parkingLot);

        });
    }


}
